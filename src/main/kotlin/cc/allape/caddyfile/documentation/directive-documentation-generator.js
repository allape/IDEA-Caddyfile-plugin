// open https://caddyserver.com/docs/caddyfile/directives

/**
 * @typedef Directive
 * @property {string} name
 * @property {string} description
 * @property {string} syntax
 * @property {string} examples
 */

function escapeHTML(str){
    const p = document.createElement("p");
    p.appendChild(document.createTextNode(str));
    return p.innerHTML;
}

/**
 * @param {HTMLElement} starter
 * @return {string}
 */
function element2html(starter) {
    let html = "";

    let next = starter;
    while (true) {
        next = next?.nextSibling;

        if (!next || !!next.id) {
            break;
        }

        if (next.classList?.contains("chroma")) {
            html += `<pre><code>${escapeHTML(next.innerText)}</code></pre>\n`
        } else if (next instanceof HTMLParagraphElement) {
            html += `${next.innerHTML || ""}\n`;
        } else {
            html += `<p>${escapeHTML(next.innerText || "")}</p>\n`;
        }
    }

    return html;
}

/**
 * @param {Document} doc
 * @param {string} name
 * @returns {Promise<Directive>}
 */
async function extractDocumentation(doc, name) {
    /** @type {Directive} */
    const dir = {
        name,
        description: "",
        syntax: "",
        examples: "",
    };

    dir.description = element2html(doc.getElementById(name.replaceAll('_', '-')));
    dir.syntax = element2html(doc.getElementById("syntax"));
    dir.examples = element2html(doc.getElementById("examples"));

    return dir;
}

/**
 * @param {string} name
 * @returns {Promise<Directive>}
 */
async function getDirectives(name) {
    const url = `https://caddyserver.com/docs/caddyfile/directives/${name}`;
    const content = await (await fetch(url)).text();
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, 'text/html');
    return extractDocumentation(doc, name);
}

async function getAllDirs() {
    const dirs = Array.from(document.querySelectorAll('#directive-table table > tbody > tr > td:nth-child(1)')).map(i => i.innerText.trim());
    const all = await Promise.all(dirs.map(dir => getDirectives(dir)));
    return dirs.map((dir, index) =>
        // language=kotlin
        `Directive(
            name = "${dir}",
            description = """${all[index].description}""",
            syntax = """${all[index].syntax}""",
            examples = """${all[index].examples}""",
        )`).join(',\n');
}

`val DIRECTIVES = arrayOf(\n${await getAllDirs()},\n)`.replace(/\$([a-zA-Z{_])/g, "$${'$$'}$1");