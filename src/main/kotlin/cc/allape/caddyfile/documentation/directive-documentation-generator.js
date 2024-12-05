// open https://caddyserver.com/docs/caddyfile/directives

async function getAllDirs() {
    const dirs = Array.from(document.querySelectorAll('#directive-table table > tbody > tr > td:nth-child(1)')).map(i => i.innerText.trim());
    return dirs.map((dir) => `"${dir}",\n`).join('');
}

`val DIRECTIVES = arrayOf(\n${await getAllDirs()})`;