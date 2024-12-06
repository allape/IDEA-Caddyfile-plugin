// open https://developer.mozilla.org/en-US/docs/Web/HTTP/MIME_types/Common_types

// https://www.iana.org/assignments/media-types/media-types.xhtml
// This contains all the MIME types and their descriptions, but too many to be useful

`val COMMON_MIME = arrayOf(\n${
    Array.from(document.querySelectorAll('#content > article > div > figure > table > tbody td:nth-child(3) > code:first-child'))
        .map(i => i.innerText)
        .map(i => `"${i}",\n`).join('')
})`