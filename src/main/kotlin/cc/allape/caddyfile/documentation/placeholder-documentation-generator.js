// open https://caddyserver.com/docs/caddyfile/concepts#placeholders

var placeholders = `{cookie.*}	{http.request.cookie.*}
{client_ip}	{http.vars.client_ip}
{dir}	{http.request.uri.path.dir}
{err.*}	{http.error.*}
{file_match.*}	{http.matchers.file.*}
{file.base}	{http.request.uri.path.file.base}
{file.ext}	{http.request.uri.path.file.ext}
{file}	{http.request.uri.path.file}
{header.*}	{http.request.header.*}
{host}	{http.request.host}
{hostport}	{http.request.hostport}
{labels.*}	{http.request.host.labels.*}
{method}	{http.request.method}
{path.*}	{http.request.uri.path.*}
{path}	{http.request.uri.path}
{port}	{http.request.port}
{query.*}	{http.request.uri.query.*}
{query}	{http.request.uri.query}
{re.*}	{http.regexp.*}
{remote_host}	{http.request.remote.host}
{remote_port}	{http.request.remote.port}
{remote}	{http.request.remote}
{rp.*}	{http.reverse_proxy.*}
{resp.*}	{http.intercept.*}
{scheme}	{http.request.scheme}
{tls_cipher}	{http.request.tls.cipher_suite}
{tls_client_certificate_der_base64}	{http.request.tls.client.certificate_der_base64}
{tls_client_certificate_pem}	{http.request.tls.client.certificate_pem}
{tls_client_fingerprint}	{http.request.tls.client.fingerprint}
{tls_client_issuer}	{http.request.tls.client.issuer}
{tls_client_serial}	{http.request.tls.client.serial}
{tls_client_subject}	{http.request.tls.client.subject}
{tls_version}	{http.request.tls.version}
{upstream_hostport}	{http.reverse_proxy.upstream.hostport}
{uri}	{http.request.uri}
{vars.*}	{http.vars.*}`.split(/\s+/)
    .map(i => i.substring(1, i.length - 1))
    .map(i => i.endsWith('*') ? i.substring(0, i.length - 1) : i)
    .map(i => `    "${i}",\n`)
    .sort()
    .join('');

`val PLACEHOLDERS = arrayOf(\n${placeholders})`;