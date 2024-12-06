// open https://caddyserver.com/docs/caddyfile/options#global-options

/**
 * @param {HTMLElement} chroma
 * @return {[string[], Record<string, string[]>]}
 */
function extract(chroma) {
    const lastDir = {};

    const dirs = [];
    const subDirs = {};

    if (!chroma) return [dirs, subDirs];

    chroma.innerText.split("\n").forEach(line => {
        if (!/^\w/.test(line.trim())) {
            return;
        }

        const indentCount = (line.split(/\w+/)[0]?.length || 1) - 1;
        const dir = line.trim().split(" ")[0];

        lastDir[indentCount] = dir;

        if (indentCount === 0) {
            dirs.push(dir);
        } else {
            const parentDir = lastDir[indentCount - 1];
            const subDir = subDirs[parentDir] || [];
            subDir.push(dir);
            subDirs[parentDir] = subDir;
        }

    });

    return [dirs, subDirs];
}

{
    const [dirs, subDirs] = extract(document.querySelector('body > main > div.article-container > div.paper.paper3 > article > pre:nth-child(8)'));

    `
val GLOBAL_DIRECTIVES = arrayOf(
    ${dirs.map(it => `"${it}",`).join("\n")}
)

val GLOBAL_SUB_DIRECTIVES = mapOf(
    ${Object.entries(subDirs).map(([key, value]) => `"${key}" to arrayOf(\n${value.map(it => `"${it}",\n`).join("")}),`).join("\n")}
)`;
}
