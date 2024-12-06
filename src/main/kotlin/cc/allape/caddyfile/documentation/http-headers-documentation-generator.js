// open https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers

`val HTTP_HEADERS = mapOf(\n${
    Array.from(document.querySelectorAll('#content > article > section dt'))
        .map(dt => [dt.innerText.split(/\s/)[0], dt.nextElementSibling?.innerHTML.trim()])
        .map(i => `"${i[0]}" to """${i[1]}""",\n`).join('')
})`.replaceAll("/en-US/docs", "https://developer.mozilla.org/en-US/docs")