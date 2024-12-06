package cc.allape.caddyfile.documentation

const val DirectiveDocURLPrefix = "https://caddyserver.com/docs/caddyfile/directives/"
const val MarcherDocURL = "https://caddyserver.com/docs/caddyfile/matchers"

data class Directive(
    val name: String,
    val description: String,
    val syntax: String,
    val examples: String,
)

/**
 * See [https://caddyserver.com/docs/caddyfile/directives](https://caddyserver.com/docs/caddyfile/directives)
 * @see ./directive-documentation-generator.js
 */
val DIRECTIVES = arrayOf(
    Directive(
        name = "abort",
        description = """<p></p>
Prevents any response to the client by immediately aborting the HTTP handler chain and closing the connection. Any concurrent, active HTTP streams on the same connection are interrupted.
<p></p>
""",
        syntax = """<p></p>
<pre><code>abort [&lt;matcher&gt;]
</code></pre>
""",
        examples = """<p></p>
Forcefully close a connection received for unknown domains when using a wildcard certificate:
<p></p>
<pre><code>*.example.com {
    @foo host foo.example.com
    handle @foo {
        respond "This is foo!" 200
    }

    handle {
		# Unhandled domains fall through to here,
		# but we don't want to accept their requests
        abort
    }
}
</code></pre>
""",
    ),
    Directive(
        name = "acme_server",
        description = """<p></p>
An embedded <a href="https://tools.ietf.org/html/rfc8555">ACME protocol</a> server handler. This allows a Caddy instance to issue certificates for any other ACME-compatible software (including other Caddy instances).
<p></p>
When enabled, requests matching the path <code>/acme/*</code> will be handled by the ACME server.
<p></p>
""",
        syntax = """<p></p>
<pre><code>acme_server [&lt;matcher&gt;] {
	ca         &lt;id&gt;
	lifetime   &lt;duration&gt;
	resolvers  &lt;resolvers...&gt;
	challenges &lt;challenges...&gt;
	allow_wildcard_names
	allow {
		domains &lt;domains...&gt;
		ip_ranges &lt;addresses...&gt;
	}
	deny {
		domains &lt;domains...&gt;
		ip_ranges &lt;addresses...&gt;
	}
}
</code></pre>
<p>

ca specifies the ID of the certificate authority with which to sign certificates. The default is local, which is Caddy's default CA, intended for locally-used, self-signed certificates, which is most common in dev environments. For broader use, it is recommended to specify a different CA to avoid confusion. If the CA with the given ID does not already exist, it will be created. See the PKI app global options to configure alternate CAs.


lifetime (Default: 12h) is a duration which specifies the validity period for issued certificates. This value must be less than the lifetime of the intermediate certificate used for signing. It is not recommended to change this unless absolutely necessary.


resolvers are the addresses of DNS resolvers to use when looking up the TXT records for solving ACME DNS challenges. Accepts network addresses defaulting to UDP and port 53 unless specified. If the host is an IP address, it will be dialed directly to resolve the upstream server. If the host is not an IP address, the addresses are resolved using the name resolution convention of the Go standard library. If multiple resolvers are specified, then one is chosen at random.


challenges sets the enabled challenge types. If not set or the directive is used without values, then all challenge types are enabled. Accepted values are: http-01, tls-alpn-01, dns-01.


allow_wildcard_names enables issuing of certificates with wildcard SAN (Subject Alternative Name)


allow, deny configure the operational policy of the acme_server. The policy evaluation follows the criteria described by Step-CA here.


domains sets the subject domain names to be allowed or denied per the policy evaluation criteria.


ip_ranges sets the subject IP ranges to be allowed or denied per the policy evaluation criteria.



</p>
<p></p>
""",
        examples = """<p></p>
To serve an ACME server with ID <code>home</code> on the domain <code>acme.example.com</code>, with the CA customized via the <a href="/docs/caddyfile/options#pki-options"><code>pki</code> global option</a>, and issuing its own certificate using the <code>internal</code> issuer:
<p></p>
<pre><code>{
	pki {
		ca home {
			name "My Home CA"
		}
	}
}

acme.example.com {
	tls {
		issuer internal {
			ca home
		}
	}
	acme_server {
		ca home
	}
}
</code></pre>
If you have another Caddy server, it can use the above ACME server to issue its own certificates:
<p></p>
<pre><code>{
	acme_ca https://acme.example.com/acme/home/directory
	acme_ca_root /path/to/home_ca_root.crt
}

example.com {
	respond "Hello, world!"
}
</code></pre>
""",
    ),
    Directive(
        name = "basic_auth",
        description = """<p></p>
Enables HTTP Basic Authentication, which can be used to protect directories and files with a username and hashed password.
<p></p>
<strong>Note that basic auth is not secure over plain HTTP.</strong> Use discretion when deciding what to protect with HTTP Basic Authentication.
<p></p>
When a user requests a resource that is protected, the browser will prompt the user for a username and password if they have not already supplied one. If the proper credentials are present in the Authorization header, the server will grant access to the resource. If the header is missing or the credentials are incorrect, the server will respond with HTTP 401 Unauthorized.
<p></p>
Caddy configuration does not accept plaintext passwords; you MUST hash them before putting them into the configuration. The <a href="/docs/command-line#caddy-hash-password"><code>caddy hash-password</code></a> command can help with this.
<p></p>
After a successful authentication, the <code>{http.auth.user.id}</code> placeholder will be available, which contains the authenticated username.
<p></p>
Prior to v2.8.0, this directive was named <code>basicauth</code>, but was renamed for consistency with other directives.
<p></p>
""",
        syntax = """<p></p>
<pre><code>basic_auth [&lt;matcher&gt;] [&lt;hash_algorithm&gt; [&lt;realm&gt;]] {
	&lt;username&gt; &lt;hashed_password&gt;
	...
}
</code></pre>
<p>

&lt;hash_algorithm&gt; is the name of the password hashing algorithm (or KDF) used for the hashes in this configuration. Default: bcrypt


&lt;realm&gt; is a custom realm name.


&lt;username&gt; is a username or user ID.


&lt;hashed_password&gt; is the password hash.

</p>
<p></p>
""",
        examples = """<p></p>
Require authentication for all requests to <code>example.com</code>:
<p></p>
<pre><code>example.com {
	basic_auth {
		# Username "Bob", password "hiccup"
		Bob $2a$14${'$'}Zkx19XLiW6VYouLHR5NmfOFU0z2GTNmpkT/5qqR7hx4IjWJPDhjvG
	}
	respond "Welcome, {http.auth.user.id}" 200
}
</code></pre>
Protect files in <code>/secret/</code> so only <code>Bob</code> can access them (and anyone can see other paths):
<p></p>
<pre><code>example.com {
	root * /srv

	basic_auth /secret/* {
		# Username "Bob", password "hiccup"
		Bob $2a$14${'$'}Zkx19XLiW6VYouLHR5NmfOFU0z2GTNmpkT/5qqR7hx4IjWJPDhjvG
	}

	file_server
}
</code></pre>
""",
    ),
    Directive(
        name = "bind",
        description = """<p></p>
Overrides the interface to which the server's socket should bind.
<p></p>
Normally, the listener binds to the empty (wildcard) interface. However, you may force the listener to bind to another hostname or IP instead. This directive accepts only a host, not a port. The port is determined by the <a href="/docs/caddyfile/concepts#addresses">site address</a> (defaulting to <code>443</code>).
<p></p>
Note that binding sites inconsistently may result in unintended consequences. For example, if two sites on the same port resolve to <code>127.0.0.1</code> and only one of those sites is configured with <code>bind 127.0.0.1</code>, then only one site will be accessible since the other will bind to the port without a specific host; the OS will choose the more specific matching socket. (Virtual hosts are not shared across different listeners.)
<p></p>
<code>bind</code> accepts <a href="/docs/conventions#network-addresses">network addresses</a>, but may not include a port.
<p></p>
""",
        syntax = """<p></p>
<pre><code>bind &lt;hosts...&gt;
</code></pre>
<p>
&lt;hosts...&gt; is the list of host interfaces to bind which to bind the listener.
</p>
<p></p>
""",
        examples = """<p></p>
To make a socket accessible only on the current machine, bind to the loopback interface (localhost):
<p></p>
<pre><code>example.com {
	bind 127.0.0.1
}
</code></pre>
To include IPv6:
<p></p>
<pre><code>example.com {
	bind 127.0.0.1 [::1]
}
</code></pre>
To bind to <code>10.0.0.1:8080</code>:
<p></p>
<pre><code>example.com:8080 {
	bind 10.0.0.1
}
</code></pre>
To bind to a Unix domain socket at <code>/run/caddy</code>:
<p></p>
<pre><code>example.com {
	bind unix//run/caddy
}
</code></pre>
To change the file permission to be writable by all users (<a href="/docs/conventions#network-addresses">defaults</a> to <code>0200</code>, which is only writable by the owner):
<p></p>
<pre><code>example.com {
	bind unix//run/caddy|0222
}
</code></pre>
To bind one domain to two different interfaces, with different responses:
<p></p>
<pre><code>example.com {
	bind 10.0.0.1
	respond "One"
}

example.com {
	bind 10.0.0.2
	respond "Two"
}
</code></pre>
""",
    ),
    Directive(
        name = "encode",
        description = """<p></p>
Encodes responses using the configured encoding(s). A typical use for encoding is compression.
<p></p>
""",
        syntax = """<p></p>
<pre><code>encode [&lt;matcher&gt;] &lt;formats...&gt; {
	# encoding formats
	gzip [&lt;level&gt;]
	zstd [&lt;level&gt;]
	
	minimum_length &lt;length&gt;

	match {
		status &lt;code...&gt;
		header &lt;field&gt; [&lt;value&gt;]
	}
}
</code></pre>
<p>

&lt;formats...&gt; is the list of encoding formats to enable. If multiple encodings are enabled, the encoding is chosen based the request's Accept-Encoding header; if the client has no strong preference (q-factor), then the first supported encoding is used.


gzip  enables Gzip compression, optionally at a specified level.


zstd  enables Zstandard compression, optionally at a specified level (possible values = default, fastest, better, best). The default compression level is roughly equivalent to the default Zstandard mode (level 3).


minimum_length  the minimum number of bytes a response should have to be encoded (default: 512).


match  is a response matcher. Only matching responses are encoded. The default looks like this:
match {
	header Content-Type application/atom+xml*
	header Content-Type application/eot*
	header Content-Type application/font*
	header Content-Type application/geo+json*
	header Content-Type application/graphql+json*
	header Content-Type application/javascript*
	header Content-Type application/json*
	header Content-Type application/ld+json*
	header Content-Type application/manifest+json*
	header Content-Type application/opentype*
	header Content-Type application/otf*
	header Content-Type application/rss+xml*
	header Content-Type application/truetype*
	header Content-Type application/ttf*
	header Content-Type application/vnd.api+json*
	header Content-Type application/vnd.ms-fontobject*
	header Content-Type application/wasm*
	header Content-Type application/x-httpd-cgi*
	header Content-Type application/x-javascript*
	header Content-Type application/x-opentype*
	header Content-Type application/x-otf*
	header Content-Type application/x-perl*
	header Content-Type application/x-protobuf*
	header Content-Type application/x-ttf*
	header Content-Type application/xhtml+xml*
	header Content-Type application/xml*
	header Content-Type font/*
	header Content-Type image/svg+xml*
	header Content-Type image/vnd.microsoft.icon*
	header Content-Type image/x-icon*
	header Content-Type multipart/bag*
	header Content-Type multipart/mixed*
	header Content-Type text/*
}

</p>
<p></p>
""",
        examples = """<p></p>
Enable Gzip compression:
<p></p>
<pre><code>encode gzip
</code></pre>
Enable Zstandard and Gzip compression (with Zstandard implicitly preferred, since it is first):
<p></p>
<pre><code>encode zstd gzip
</code></pre>
And in a full site, compressing static files served by <a href="file_server"><code>file_server</code></a>:
<p></p>
<pre><code>example.com {
	root * /srv
	encode zstd gzip
	file_server
}
</code></pre>
""",
    ),
    Directive(
        name = "error",
        description = """<p></p>
Triggers an error in the HTTP handler chain, with an optional message and recommended HTTP status code.
<p></p>
This handler does not write a response. Instead, it's meant to be paired with the <a href="handle_errors"><code>handle_errors</code></a> directive to invoke your custom error handling logic.
<p></p>
""",
        syntax = """<p></p>
<pre><code>error [&lt;matcher&gt;] &lt;status&gt;|&lt;message&gt; [&lt;status&gt;] {
    message &lt;text&gt;
}
</code></pre>
<p>
&lt;status&gt; is the HTTP status code to write. Default is 500.
&lt;message&gt; is the error message. Default is no error message.
message is an alternate way to provide an error message; convenient if it is multiple lines.
</p>
<p></p>
To clarify, the first non-matcher argument can be either a 3-digit status code, or an error message string. If it is an error message, the next argument can be the status code.
<p></p>
""",
        examples = """<p></p>
Trigger an error on certain request paths, and use <a href="handle_errors"><code>handle_errors</code></a> to write a response:
<p></p>
<pre><code>example.com {
	root * /srv

	# Trigger errors for certain paths
    error /private* "Unauthorized" 403
	error /hidden* "Not found" 404

    # Handle the error by serving an HTML page 
    handle_errors {
        rewrite * /{err.status_code}.html
		file_server
    }

	file_server
}
</code></pre>
""",
    ),
    Directive(
        name = "file_server",
        description = """<p></p>
A static file server that supports real and virtual file systems. It forms file paths by appending the request's URI path to the <a href="root">site's root path</a>.
<p></p>
By default, it enforces canonical URIs; meaning HTTP redirects will be issued for requests to directories that do not end with a trailing slash (to add it), or requests to files that have a trailing slash (to remove it). However, redirects are not issued if an internal rewrite modifies the last element of the path (the filename).
<p></p>
Most often, the <code>file_server</code> directive is paired with the <a href="root"><code>root</code></a> directive to set the file root for the whole site. This directive also has a <code>root</code> subdirective (see below) to set the root only for this handler (not recommended). Note that a site root does not carry sandbox guarantees: the file server does prevent directory traversal from path components, but symbolic links within the root can still allow accesses outside of the root.
<p></p>
When errors occur (e.g. file not found <code>404</code>, permission denied <code>403</code>), the error routes will be invoked. Use the <a href="handle_errors"><code>handle_errors</code></a> directive to define error routes, and display custom error pages.
<p></p>
When using <code>browse</code>, the default output is produced by the the HTML template. Clients may request the directory listing as either JSON or plaintext, by using the <code>Accept: application/json</code> or <code>Accept: text/plain</code> headers respectively. The JSON output can be useful for scripting, and the plaintext output can be useful for human terminal usage.
<p></p>
""",
        syntax = """<p></p>
<pre><code>file_server [&lt;matcher&gt;] [browse] {
	fs            &lt;backend...&gt;
	root          &lt;path&gt;
	hide          &lt;files...&gt;
	index         &lt;filenames...&gt;
	browse        [&lt;template_file&gt;] {
		reveal_symlinks
	}
	precompressed &lt;formats...&gt;
	status        &lt;status&gt;
	disable_canonical_uris
	pass_thru
}
</code></pre>
<p>

fs  specifies an alternate (perhaps virtual) file system to use. Any Caddy module in the caddy.fs namespace can be used here. Any root path/prefix will still apply to alternate file system modules. By default, the local disk is used.
xcaddy v0.4.0 introduces the --embed flag to embed a filesystem tree into the custom Caddy build, and registers an fs module named embedded which allows your static site to be distributed as a Caddy executable.


root  sets the path to the site root. It's similar to the root directive except it applies to this file server instance only and overrides any other site root that may have been defined. Default: {http.vars.root} or the current working directory. Note: This subdirective only changes the root for this handler. For other directives (like try_files or templates) to know the same site root, use the root directive instead.


hide  is a list of files or folders to hide; if requested, the file server will pretend they do not exist. Accepts placeholders and glob patterns. Note that these are file system paths, NOT request paths. In other words, relative paths use the current working directory as a base, NOT the site root; and all paths are transformed to their absolute form before comparisons (if possible). Specifying a file name or pattern without a path separator will hide all files with a matching name regardless of its location; otherwise, a path prefix match will be attempted, and then a globular match. Since this is a Caddyfile config, the active configuration file(s) will be added by default.


index  is a list of filenames to look for as index files. Default: index.html index.txt


browse  enables file listings for requests to directories that do not have an index file.


&lt;template_file&gt;  is an optional custom template file to use for directory listings. Defaults to the template that can be extracted using the command caddy file-server export-template, which will print the defaut template to stdout. The embedded template can also be found here in the source code . Browse templates can use actions from the standard templates module as well.


reveal_symlinks  enables revealing the targets of symbolic links in directory listings. By default, the symlink targets are hidden, and only the link file itself is shown.




precompressed  is the list of encoding formats to search for precompressed sidecar files. Arguments are an ordered list of encoding formats to search for precompressed sidecar files. Supported formats are gzip (.gz), zstd (.zst) and br (.br).
All file lookups will look for the existence of the uncompressed file first. Once found Caddy will look for sidecar files with the file extension of each enabled format. If a precompressed sidecar file is found, Caddy will respond with the precompressed file, with the Content-Encoding response header set appropriately. Otherwise, Caddy will respond with the uncompressed file as normal. If the encode directive is enabled, then it may compress the response on-the-fly if not precompressed.


status  is an optional status code override to be used when writing the response. Particularly useful when responding to a request with a custom error page. Can be a 3-digit status code, For example: 404. Placeholders are supported. By default, the written status code will typically be 200, or 206 for partial content.


disable_canonical_uris  disables the default behaviour of redirecting (to add a trailing slash if the request path is a directory, or remove the trailing slash if the request path is a file). Note that by default, canonicalization will not happen if the last element of the request's path (the filename) underwent an internal rewrite, to avoid clobbering an explicit rewrite with implicit behaviour.


pass_thru  enables pass-thru mode, which continues to the next HTTP handler in the route if the requested file is not found, instead of triggering a 404 error (invoking handle_errors routes). Practically, this is only useful inside of a route block with other handler directives following file_server, because this directive is effectively ordered last.

</p>
<p></p>
""",
        examples = """<p></p>
A static file server out of the current directory:
<p></p>
<pre><code>file_server
</code></pre>
With file listings enabled:
<p></p>
<pre><code>file_server browse
</code></pre>
Only serve static files within the <code>/static</code> folder:
<p></p>
<pre><code>file_server /static/*
</code></pre>
The <code>file_server</code> directive is usually paired with the <a href="root"><code>root</code> directive</a> to set the root path from which to serve files:
<p></p>
<pre><code>example.com {
	root * /srv
	file_server
}
</code></pre>
<p>
If you're running Caddy as a systemd service, reading files from /home will not work, because the caddy user does not have "executable" permission on the /home directory (necessary for traversal). It's recommended that you place your files in /srv or /var/www/html instead.
</p>
<p></p>
Hide all <code>.git</code> folders and their contents:
<p></p>
<pre><code>file_server {
	hide .git
}
</code></pre>
If supported by the client (<code>Accept-Encoding</code> header) checks the existence of precompressed files along side the requested file. So if <code>/path/to/file</code> is requested, it checks for <code>/path/to/file.zst</code>, <code>/path/to/file.br</code> and <code>/path/to/file.gz</code> in that order and serves the first available file with corresponding Content-Encoding:
<p></p>
<pre><code>file_server {
	precompressed zstd br gzip
}
</code></pre>
""",
    ),
    Directive(
        name = "forward_auth",
        description = """<p></p>
An opinionated directive which proxies a clone of the request to an authentication gateway, which can decide whether handling should continue, or needs to be sent to a login page.
<p></p>
<p>
Syntax
Expanded Form
Examples

Authelia
Tailscale


</p>
<p></p>
Caddy's <a href="/docs/caddyfile/directives/reverse_proxy"><code>reverse_proxy</code></a> is capable of performing "pre-check requests" to an external service, but this directive is tailored specifically for the authentication usecase. This directive is actually just a convenient way to use a longer, more common configuration (below).
<p></p>
This directive makes a <code>GET</code> request to the configured upstream with the <code>uri</code> rewritten:
<p></p>
<p>
If the upstream responds with a 2xx status code, then access is granted and the header fields in copy_headers are copied to the original request, and handling continues.
Otherwise, if the upstream responds with any other status code, then the upstream's response is copied back to the client. This response should typically involve a redirect to login page of the authentication gateway.
</p>
<p></p>
If this behaviour is not exactly what you want, you may take the <a href="#expanded-form">expanded form</a> below as a basis and customize it to your needs.
<p></p>
All the subdirectives of <a href="/docs/caddyfile/directives/reverse_proxy"><code>reverse_proxy</code></a> are supported, and passed through to the underlying <code>reverse_proxy</code> handler.
<p></p>
""",
        syntax = """<p></p>
<pre><code>forward_auth [&lt;matcher&gt;] [&lt;upstreams...&gt;] {
	uri          &lt;to&gt;
	copy_headers &lt;fields...&gt; {
		&lt;fields...&gt;
	}
}
</code></pre>
<p>

&lt;upstreams...&gt; is a list of upstreams (backends) to which to send auth requests.


uri is the URI (path and query) to set on the request sent to the upstream. This will usually be the verification endpoint of the authentication gateway.


copy_headers is a list of HTTP header fields to copy from the response to the original request, when the request has a success status code.
The field can be renamed by using &gt; followed by the new name, for example Before&gt;After.
A block may be used to list all the fields, one per line, if you prefer for readability.

</p>
<p></p>
Since this directive is an opinionated wrapper over a reverse proxy, you can use any of <a href="/docs/caddyfile/directives/reverse_proxy#syntax"><code>reverse_proxy</code></a>'s subdirectives to customize it.
<p></p>
""",
        examples = """<p></p>
""",
    ),
    Directive(
        name = "fs",
        description = """<p></p>
Sets which file system should be used for performing file I/O.
<p></p>
This could let you connect to a remote filesystem running in the cloud, or a database with a file-like interface, or even to read from files embedded within the Caddy binary.
<p></p>
First, you must declare a file system name using the <a href="/docs/caddyfile/options#filesystem"><code>filesystem</code> global option</a>, then you can use this directive to specify which file system to use.
<p></p>
This directive is often used in conjunction with the <a href="file_server"><code>file_server</code> directive</a> to serve static files, or the <a href="try_files"><code>try_files</code> directive</a> to perform rewrites based on the existence of files. Typically also used with <a href="root"><code>root</code> directive</a> to set the root path within the file system.
<p></p>
""",
        syntax = """<p></p>
<pre><code>fs [&lt;matcher&gt;] &lt;filesystem&gt;
</code></pre>
""",
        examples = """<p></p>
Using an file system named <code>foo</code>, using an imaginary module named <code>custom</code> which might require authentication:
<p></p>
<pre><code>{
	filesystem foo custom {
		api_key abc123
	}
}

example.com {
	fs foo
	root /srv
	file_server
}
</code></pre>
To only serve images from the <code>foo</code> file system, and the rest from the default file system:
<p></p>
<pre><code>example.com {
	fs /images* foo
	root /srv
	file_server
}
</code></pre>
""",
    ),
    Directive(
        name = "handle",
        description = """<p></p>
Evaluates a group of directives mutually exclusively from other <code>handle</code> blocks at the same level of nesting.
<p></p>
In other words, when multiple <code>handle</code> directives appear in sequence, only the first <em>matching</em> <code>handle</code> block will be evaluated. A handle with no matcher acts like a <em>fallback</em> route.
<p></p>
The <code>handle</code> directives are sorted according to the <a href="/docs/caddyfile/directives#sorting-algorithm">directive sorting algorithm</a> by their matchers. The <a href="handle_path"><code>handle_path</code></a> directive is a special case which sorts at the same priority as a <code>handle</code> with a path matcher.
<p></p>
Handle blocks can be nested if needed. Only HTTP handler directives can be used inside handle blocks.
<p></p>
""",
        syntax = """<p></p>
<pre><code>handle [&lt;matcher&gt;] {
	&lt;directives...&gt;
}
</code></pre>
<p>
&lt;directives...&gt; is a list of HTTP handler directives or directive blocks, one per line, just like would be used outside of a handle block.
</p>
<p></p>
""",
        examples = """<p></p>
Handle requests in <code>/foo/</code> with the static file server, and other requests with the reverse proxy:
<p></p>
<pre><code>example.com {
	handle /foo/* {
		file_server
	}

	handle {
		reverse_proxy 127.0.0.1:8080
	}
}
</code></pre>
You can mix <code>handle</code> and <a href="handle_path"><code>handle_path</code></a> in the same site, and they will still be mutually exclusive from each other:
<p></p>
<pre><code>example.com {
	handle_path /foo/* {
		# The path has the "/foo" prefix stripped
	}

	handle /bar/* {
		# The path still retains "/bar"
	}
}
</code></pre>
You can nest <code>handle</code> blocks to create more complex routing logic:
<p></p>
<pre><code>example.com {
	handle /foo* {
		handle /foo/bar* {
			# This block only matches paths under /foo/bar
		}

		handle {
			# This block matches everything else under /foo/
		}
	}

	handle {
		# This block matches everything else (acts as a fallback)
	}
}
</code></pre>
""",
    ),
    Directive(
        name = "handle_errors",
        description = """<p></p>
Sets up error handlers.
<p></p>
When the normal HTTP request handlers return an error, normal processing stops and the error handlers are invoked. Error handlers form a route which is just like normal routes, and they can do anything that normal routes can do. This enables great control and flexibility when handling errors during HTTP requests. For example, you can serve static error pages, templated error pages, or reverse proxy to another backend to handle errors.
<p></p>
The directive may be repeated with different status codes to handle different errors differently. If no status codes are specified, then it will match any error, acting as a fallback if any other error handlers does not match.
<p></p>
A request's context is carried into error routes, so any values set on the request context such as <a href="root">site root</a> or <a href="vars">vars</a> will be preserved in error handlers, too. Additionally, <a href="#placeholders">new placeholders</a> are available when handling errors.
<p></p>
Note that certain directives, for example <a href="reverse_proxy"><code>reverse_proxy</code></a> which may write a response with an HTTP status which is classified as an error, will <em>not</em> trigger the error routes.
<p></p>
You may use the <a href="error"><code>error</code></a> directive to explicitly trigger an error based on your own routing decisions.
<p></p>
""",
        syntax = """<p></p>
<pre><code>handle_errors [&lt;status_codes...&gt;] {
	&lt;directives...&gt;
}
</code></pre>
<p>

&lt;status_codes...&gt; is one or more HTTP status codes to match against the error being handled. The status codes may be 3-digit numbers, or a special case of 4xx or 5xx which match against all status codes in the range of 400-499 or 500-599, respectively. If no status codes are specified, then it will match any error, acting as a fallback if any other error handlers does not match.


&lt;directives...&gt; is a list of HTTP handler directives and matchers, one per line.

</p>
<p></p>
""",
        examples = """<p></p>
Custom error pages based on the status code (i.e. a page called <code>404.html</code> for <code>404</code> errors). Note that <a href="file_server"><code>file_server</code></a> preserves the error's HTTP status code when run in <code>handle_errors</code> (assumes you set a <a href="root">site root</a> in your site beforehand):
<p></p>
<pre><code>handle_errors {
	rewrite * /{err.status_code}.html
	file_server
}
</code></pre>
A single error page that uses <a href="templates"><code>templates</code></a> to write a custom error message:
<p></p>
<pre><code>handle_errors {
	rewrite * /error.html
	templates
	file_server
}
</code></pre>
If you want to provide custom error pages only for some error codes, you can check the existence of the custom error files beforehand with a <a href="/docs/caddyfile/matchers#file"><code>file</code></a> matcher:
<p></p>
<pre><code>handle_errors {
	@custom_err file /err-{err.status_code}.html /err.html
	handle @custom_err {
		rewrite * {file_match.relative}
		file_server
	}
	respond "{err.status_code} {err.status_text}"
}
</code></pre>
Reverse proxy to a professional server that is highly qualified for handling HTTP errors and improving your day 😸:
<p></p>
<pre><code>handle_errors {
	rewrite * /{err.status_code}
	reverse_proxy https://http.cat {
		header_up Host {upstream_hostport}
		replace_status {err.status_code}
	}
}
</code></pre>
Simply use <a href="respond"><code>respond</code></a> to return the error code and name
<p></p>
<pre><code>handle_errors {
	respond "{err.status_code} {err.status_text}"
}
</code></pre>
To handle specific error codes differently:
<p></p>
<pre><code>handle_errors 404 410 {
	respond "It's a 404 or 410 error!"
}

handle_errors 5xx {
	respond "It's a 5xx error."
}

handle_errors {
	respond "It's another error"
}
</code></pre>
The above behaves the same as the below, which uses an <a href="/docs/caddyfile/matchers#expression"><code>expression</code></a> matcher against the status codes, and using <a href="handle"><code>handle</code></a> for mutual exclusivity:
<p></p>
<pre><code>handle_errors {
	@404-410 `{err.status_code} in [404, 410]`
	handle @404-410 {
		respond "It's a 404 or 410 error!"
	}

	@5xx `{err.status_code} &gt;= 500 &amp;&amp; {err.status_code} &lt; 600`
	handle @5xx {
		respond "It's a 5xx error."
	}

	handle {
		respond "It's another error"
	}
}
</code></pre>
""",
    ),
    Directive(
        name = "handle_path",
        description = """<p></p>
Works the same as the <a href="handle"><code>handle</code> directive</a>, but implicitly uses <a href="uri"><code>uri strip_prefix</code></a> to strip the matched path prefix.
<p></p>
Handling a request matching a certain path (while stripping that path from the request URI) is a common enough use case that it has its own directive for convenience.
<p></p>
""",
        syntax = """<p></p>
<pre><code>handle_path &lt;path_matcher&gt; {
	&lt;directives...&gt;
}
</code></pre>
<p>
&lt;directives...&gt; is a list of HTTP handler directives or directive blocks, one per line, just like would be used outside of a handle_path block.
</p>
<p></p>
Only a single <a href="/docs/caddyfile/matchers#path-matchers">path matcher</a> is accepted, and is required; you cannot use named matchers with <code>handle_path</code>.
<p></p>
""",
        examples = """<p></p>
This configuration:
<p></p>
<pre><code>handle_path /prefix/* {
	...
}
</code></pre>
👆 is effectively the same as this 👇, but the <code>handle_path</code> form 👆 is slightly more succinct
<p></p>
<pre><code>handle /prefix/* {
	uri strip_prefix /prefix
	...
}
</code></pre>
A full Caddyfile example, where <code>handle_path</code> and <code>handle</code> are mutually exclusive; but, be aware of the <a href="https://caddy.community/t/the-subfolder-problem-or-why-cant-i-reverse-proxy-my-app-into-a-subfolder/8575">subfolder problem <img src="/old/resources/images/external-link.svg" class="external-link"></a>
<p></p>
<pre><code>example.com {
	# Serve your API, stripping the /api prefix
	handle_path /api/* {
		reverse_proxy localhost:9000
	}

	# Serve your static site
	handle {
		root * /srv
		file_server
	}
}
</code></pre>
""",
    ),
    Directive(
        name = "header",
        description = """<p></p>
Manipulates HTTP response header fields. It can set, add, and delete header values, or perform replacements using regular expressions.
<p></p>
By default, header operations are performed immediately unless any of the headers are being deleted (<code>-</code> prefix) or setting a default value (<code>?</code> prefix). In those cases, the header operations are automatically deferred until the time they are being written to the client.
<p></p>
To manipulate HTTP request headers, you may use the <a href="request_header"><code>request_header</code></a> directive.
<p></p>
""",
        syntax = """<p></p>
<pre><code>header [&lt;matcher&gt;] [[+|-|?|&gt;]&lt;field&gt; [&lt;value&gt;|&lt;find&gt;] [&lt;replace&gt;]] {
	# Add
	+&lt;field&gt; &lt;value&gt;

	# Set
	&lt;field&gt; &lt;value&gt;

	# Set with defer
	&gt;&lt;field&gt; &lt;value&gt;

	# Delete
	-&lt;field&gt;

	# Replace
	&lt;field&gt; &lt;find&gt; &lt;replace&gt;

	# Replace with defer
	&gt;&lt;field&gt; &lt;find&gt; &lt;replace&gt;

	# Default
	?&lt;field&gt; &lt;value&gt;

	[defer]
}
</code></pre>
<p>

&lt;field&gt; is the name of the header field.
With no prefix, the field is set (overwritten).
Prefix with + to add the field instead of overwriting (setting) the field if it already exists; header fields can appear more than once in a request.
Prefix with - to delete the field. The field may use prefix or suffix * wildcards to delete all matching fields.
Prefix with ? to set a default value for the field. The field is only written if it doesn't yet exist.
Prefix with &gt; to set the field, and enable defer, as a shortcut.


&lt;value&gt; is the header field value, when adding or setting a field.


&lt;find&gt; is the substring or regular expression to search for.


&lt;replace&gt; is the replacement value; required if performing a search-and-replace. Use $1 or $2 and so on to reference capture groups from the search pattern. If the replacement value is "", then the matching text is removed from the value. See the Go documentation for details.


defer will force the header operations to be deferred until the response is being written out to the client. This is automatically enabled if any of the header fields are being deleted with -, when setting a default value with ?, or when having used the &gt; prefix.

</p>
<p></p>
For multiple header manipulations, you can open a block and specify one manipulation per line in the same way.
<p></p>
When using the <code>?</code> prefix to set a default header value, it is automatically separated into its own <code>header</code> handler, if it was in a <code>header</code> block with multiple header operations. <a href="/docs/modules/http.handlers.headers#response/require">Under the hood</a>, using <code>?</code> configures a <a href="/docs/caddyfile/response-matchers">response matcher</a> which applies to the directive's entire handler, which only applies the header operations (like <code>defer</code>), but only if the field is not yet set.
<p></p>
""",
        examples = """<p></p>
Set a custom header field on all requests:
<p></p>
<pre><code>header Custom-Header "My value"
</code></pre>
Strip the "Hidden" header field:
<p></p>
<pre><code>header -Hidden
</code></pre>
Replace <code>http://</code> with <code>https://</code> in any Location header:
<p></p>
<pre><code>header Location http:// https://
</code></pre>
Set security and privacy headers on all pages: (<strong>WARNING:</strong> only use if you understand the implications!)
<p></p>
<pre><code>header {
	# disable FLoC tracking
	Permissions-Policy interest-cohort=()

	# enable HSTS
	Strict-Transport-Security max-age=31536000;

	# disable clients from sniffing the media type
	X-Content-Type-Options nosniff

	# clickjacking protection
	X-Frame-Options DENY
}
</code></pre>
Multiple header directives that are intended to be mutually-exclusive:
<p></p>
<pre><code>route {
	header           Cache-Control max-age=3600
	header /static/* Cache-Control max-age=31536000
}
</code></pre>
Set a default cache expiration if upstream doesn't define one:
<p></p>
<pre><code>header ?Cache-Control "max-age=3600"
reverse_proxy upstream:443
</code></pre>
To override the cache expiration that a proxy upstream had set for paths starting with <code>/no-cache</code>; enabling <code>defer</code> is necessary to ensure the header is set <em>after</em> the proxy writes its headers:
<p></p>
<pre><code>header /no-cache* &gt;Cache-Control nocache
reverse_proxy upstream:443
</code></pre>
To perform a deferred update of a <code>Set-Cookie</code> header to add <code>SameSite=None</code>; a regexp capture is used to grab the existing value, and <code>$1</code> re-inserts it at the start with the additional option appended:
<p></p>
<pre><code>header &gt;Set-Cookie (.*) "$1; SameSite=None;"
</code></pre>
""",
    ),
    Directive(
        name = "import",
        description = """<p></p>
Includes a <a href="/docs/caddyfile/concepts#snippets">snippet</a> or file, replacing this directive with the contents of the snippet or file.
<p></p>
This directive is a special case: it is evaluated before the structure is parsed, and it can appear anywhere in the Caddyfile.
<p></p>
""",
        syntax = """<p></p>
<pre><code>import &lt;pattern&gt; [&lt;args...&gt;]
</code></pre>
<p>

&lt;pattern&gt; is the filename, glob pattern, or name of snippet to include. Its contents will replace this line as if that file's contents appeared here to begin with.
It is an error if a specific file cannot be found, but an empty glob pattern is not an error.
If importing a specific file, a warning will be emitted if the file is empty.
If the pattern is a filename or glob, it is always relative to the file the import appears in.
If using a glob pattern * as the final path segment, hidden files (i.e. files starting with a .) are ignored. To import hidden files, use .* as the final segment.


&lt;args...&gt; is an optional list of arguments to pass to the imported tokens. This placeholder is a special case and is evaluated at Caddyfile-parse-time, not at run-time. They can be used in various forms, similarly to Go's slice syntax:

{args[n]} where n is the 0-based positional index of the parameter
{args[:]} where all the arguments are inserted
{args[:m]} where the arguments before m are inserted
{args[n:]} where the arguments beginning with n are inserted
{args[n:m]} where the arguments in the range between n and m are inserted

For the forms that insert many tokens, the placeholder must be a token on its own, it cannot be part of another token. In other words, it must have spaces around it, and cannot be in quotes.
Note that prior to v2.7.0, the syntax was {args.N} but this form was deprecated in favor of the more flexible syntax above.

</p>
<p></p>
""",
        examples = """<p></p>
Import all files in an adjacent sites-enabled folder (except hidden files):
<p></p>
<pre><code>import sites-enabled/*
</code></pre>
Import a snippet that sets CORS headers using an import argument:
<p></p>
<pre><code>(cors) {
	@origin header Origin {args[0]}
	header @origin Access-Control-Allow-Origin "{args[0]}"
	header @origin Access-Control-Allow-Methods "OPTIONS,HEAD,GET,POST,PUT,PATCH,DELETE"
}

example.com {
	import cors example.com
}
</code></pre>
Import a snippet which takes a list of proxy upstreams as arguments:
<p></p>
<pre><code>(https-proxy) {
	reverse_proxy {args[:]} {
		transport http {
			tls
		}
	}
}

example.com {
	import https-proxy 10.0.0.1 10.0.0.2 10.0.0.3
}
</code></pre>
Import a snippet which creates a proxy with a prefix rewrite rule as the first argument:
<p></p>
<pre><code>(proxy-rewrite) {
	rewrite * {args[0]}{uri}
	reverse_proxy {args[1:]}
}

example.com {
	import proxy-rewrite /api 10.0.0.1 10.0.0.2 10.0.0.3
}
</code></pre>
""",
    ),
    Directive(
        name = "intercept",
        description = """<p></p>
A generalized abstraction of the <a href="reverse_proxy#intercepting-responses">response interception</a> feature from the <a href="reverse_proxy"><code>reverse_proxy</code> directive</a>. This may be used with any handler that produces responses, including those from plugins like <a href="https://frankenphp.dev/">FrankenPHP</a>'s <code>php_server</code>.
<p></p>
This directive allows you to <a href="/docs/caddyfile/response-matchers">match responses</a>, and the first matching <code>handle_response</code> route or <code>replace_status</code> will be invoked. When invoked, the original response body is held back, giving the opportunity to that route to write a different response body, with a new status code or with any necessary response header manipulations. If the route does <em>not</em> write a new response body, then original response body is written instead.
<p></p>
""",
        syntax = """<p></p>
<pre><code>intercept [&lt;matcher&gt;] {
	@name {
		status &lt;code...&gt;
		header &lt;field&gt; [&lt;value&gt;]
	}

	replace_status [&lt;matcher&gt;] &lt;code&gt;

	handle_response [&lt;matcher&gt;] {
		&lt;directives...&gt;
	}
}
</code></pre>
<p>

@name is the name of a response matcher. As long as each response matcher has a unique name, multiple matchers can be defined. A response can be matched on the status code and presence or value of a response header.


replace_status  simply changes the status code of response when matched by the given matcher.


handle_response  defines the route to execute when matched by the given matcher (or, if a matcher is omitted, all responses). The first matching block will be applied. Inside a handle_response block, any other directives can be used.

</p>
<p></p>
Within <code>handle_response</code> routes, the following placeholders are available to pull information from the original response:
<p></p>
<p>

{resp.status_code} The status code of the original response.


{resp.header.*} The headers from the original response.

</p>
<p></p>
""",
        examples = """<p></p>
When using <a href="https://frankenphp.dev/">FrankenPHP</a>'s <code>php_server</code>, you can use <code>intercept</code> to implement <code>X-Accel-Redirect</code> support, serving static files as requested by the PHP app:
<p></p>
<pre><code>localhost {
	root * /srv

	intercept {
		@accel header X-Accel-Redirect *
		handle_response @accel {
			root * /path/to/private/files
			rewrite {resp.header.X-Accel-Redirect}
			method GET
			file_server
		}
	}

	php_server
}
</code></pre>
""",
    ),
    Directive(
        name = "invoke",
        description = """<p></p>
<i>⚠️ Experimental</i>
<p></p>
Invokes a <a href="/docs/caddyfile/concepts#named-routes">named route</a>.
<p></p>
This is useful when paired with HTTP handler directives that have their own in-memory state, or if they are expensive to provision on load. If you have hundreds of sites or more, invoking a named route can help reduce memory usage.
<p></p>
<p>
Unlike import, invoke does not support arguments, but you may use vars to define variables that can be used within the named route.
</p>
<p></p>
""",
        syntax = """<p></p>
<pre><code>invoke [&lt;matcher&gt;] &lt;route-name&gt;
</code></pre>
<p>
&lt;route-name&gt; is the name of the previously defined route that should be invoked. If the route is not found, then an error will be triggered.
</p>
<p></p>
""",
        examples = """<p></p>
Defines a <a href="/docs/caddyfile/concepts#named-routes">named route</a> with a <a href="/docs/caddyfile/directives/reverse_proxy"><code>reverse_proxy</code></a> which can be reused in multiple sites, with the same in-memory load balancing state reused for every site.
<p></p>
<pre><code>&amp;(app-proxy) {
	reverse_proxy app-01:8080 app-02:8080 app-03:8080 {
		lb_policy least_conn
		health_uri /healthz
		health_interval 5s
	}
}

# Apex domain allows accessing the app via an /app subpath
# and the main site otherwise.
example.com {
	handle_path /app* {
		invoke app-proxy
	}

	handle {
		root * /srv
		file_server
	}
}

# The app is also accessible via a subdomain.
app.example.com {
	invoke app-proxy
}
</code></pre>
""",
    ),
    Directive(
        name = "log",
        description = """<p></p>
Enables and configures HTTP request logging (also known as access logs).
<p></p>
<p>
To configure Caddy's runtime logs, see the log global option instead.
</p>
<p></p>
The <code>log</code> directive applies to the hostnames of the site block it appears in, unless overridden with the <code>hostnames</code> subdirective.
<p></p>
When configured, by default all requests to the site will be logged. To conditionally skip some requests from logging, use the <a href="log_skip"><code>log_skip</code> directive</a>.
<p></p>
To add custom fields to the log entries, use the <a href="log_append"><code>log_append</code> directive</a>.
<p></p>
<p>
Syntax
Output modules

stderr
stdout
discard
file
net


Format modules

console
json
filter

delete
rename
replace
ip_mask
query
cookie
regexp
hash


append


Examples
</p>
<p></p>
By default, headers with potentially sensitive information (<code>Cookie</code>, <code>Set-Cookie</code>, <code>Authorization</code> and <code>Proxy-Authorization</code>) will be logged as <code>REDACTED</code> in access logs. This behaviour can be disabled with the <a href="/docs/caddyfile/options#log-credentials"><code>log_credentials</code></a> global server option.
<p></p>
""",
        syntax = """<p></p>
<pre><code>log [&lt;logger_name&gt;] {
	hostnames &lt;hostnames...&gt;
	no_hostname
	output &lt;writer_module&gt; ...
	format &lt;encoder_module&gt; ...
	level  &lt;level&gt;
}
</code></pre>
<p>

logger_name  is an optional override of the logger name for this site.
By default, a logger name is generated automatically, e.g. log0, log1, and so on depending on the order of the sites in the Caddyfile. This is only useful if you wish to reliably refer to the output of this logger from another logger defined in global options. See an example below.


hostnames  is an optional override of the hostnames that this logger applies to.
By default, the logger applies to the hostnames of the site block it appears in, i.e. the site addresses. This is useful if you wish to define different loggers per subdomain in a wildcard site block. See an example below.


no_hostname  prevents the logger from being associated with any of the site block's hostnames. By default, the logger is associated with the site address that the log directive appears in.
This is useful when you want to log requests to different files based on some condition, such as the request path or method, using the log_name directive.


output  configures where to write the logs. See output modules below.
Default: stderr.


format  describes how to encode, or format, the logs. See format modules below.
Default: console if stderr is detected to be a terminal, json otherwise.


level  is the minimum entry level to log. Default: INFO.
Note that access logs currently only emit INFO and ERROR level logs.

</p>
<p></p>
""",
        examples = """<p></p>
Enable access logging to the default logger.
<p></p>
In other words, by default this logs to <code>stderr</code>, but this can be changed by reconfiguring the <code>default</code> logger with the <a href="/docs/caddyfile/options#log"><code>log</code> global option</a>:
<p></p>
<pre><code>example.com {
	log
}
</code></pre>
Write logs to a file (with log rolling, which is enabled by default):
<p></p>
<pre><code>example.com {
	log {
		output file /var/log/access.log
	}
}
</code></pre>
Customize log rolling:
<p></p>
<pre><code>example.com {
	log {
		output file /var/log/access.log {
			roll_size 1gb
			roll_keep 5
			roll_keep_for 720h
		}
	}
}
</code></pre>
Delete the <code>User-Agent</code> request header from the logs:
<p></p>
<pre><code>example.com {
	log {
		format filter {
			request&gt;headers&gt;User-Agent delete
		}
	}
}
</code></pre>
Redact multiple sensitive cookies. (Note that some sensitive headers are logged with empty values by default; see the <a href="/docs/caddyfile/options#log-credentials"><code>log_credentials</code> global option</a> to enable logging <code>Cookie</code> header values):
<p></p>
<pre><code>example.com {
	log {
		format filter {
			request&gt;headers&gt;Cookie cookie {
				replace session REDACTED
				delete secret
			}
		}
	}
}
</code></pre>
Mask the remote address from the request, keeping the first 16 bits (i.e. 255.255.0.0) for IPv4 addresses, and the first 32 bits from IPv6 addresses.
<p></p>
Note that as of Caddy v2.7, both <code>remote_ip</code> and <code>client_ip</code> are logged, where <code>client_ip</code> is the "real IP" when <a href="/docs/caddyfile/options#trusted-proxies"><code>trusted_proxies</code></a> is configured:
<p></p>
<pre><code>example.com {
	log {
		format filter {
			request&gt;remote_ip ip_mask 16 32
			request&gt;client_ip ip_mask 16 32
		}
	}
}
</code></pre>
To append a server ID from an environment variable to all log entries, and chain it with a <code>filter</code> to delete a header:
<p></p>
<pre><code>example.com {
	log {
		format append {
			server_id {env.SERVER_ID}
			wrap filter {
				request&gt;headers&gt;Cookie delete
			}
		}
	}
}
</code></pre>
<span id="wildcard-logs"> To write separate log files for each subdomain in a <a href="/docs/caddyfile/patterns#wildcard-certificates">wildcard site block</a>, by overriding <code>hostnames</code> for each logger. This uses a <a href="/docs/caddyfile/concepts#snippets">snippet</a> to avoid repetition:</span>
<p></p>
<pre><code>(subdomain-log) {
	log {
		hostnames {args[0]}
		output file /var/log/{args[0]}.log
	}
}

*.example.com {
	import subdomain-log foo.example.com
	@foo host foo.example.com
	handle @foo {
		respond "foo"
	}

	import subdomain-log bar.example.com
	@bar host bar.example.com
	handle @bar {
		respond "bar"
	}
}
</code></pre>
<span id="multiple-outputs"> To write the access logs for a particular subdomain to two different files, with different formats (one with <a href="https://github.com/caddyserver/transform-encoder"><code>transform-encoder</code> plugin <img src="/old/resources/images/external-link.svg" class="external-link"></a> and the other with <a href="#json"><code>json</code></a>).</span>
<p></p>
This works by overriding the logger name as <code>foo</code> in the site block, then including the access logs produced by that logger in the two loggers in global options with <code>include http.log.access.foo</code>:
<p></p>
<pre><code>{
	log access-formatted {
		include http.log.access.foo
		output file /var/log/access-foo.log
		format transform "{common_log}"
	}

	log access-json {
		include http.log.access.foo
		output file /var/log/access-foo.json
		format json
	}
}

foo.example.com {
	log foo
}
</code></pre>
""",
    ),
    Directive(
        name = "log_append",
        description = """<p></p>
Appends a field to the access log for the current request.
<p></p>
This should be used alongside the <a href="log"><code>log</code> directive</a> which is required to enable access logging in the first place.
<p></p>
The value may be a static string, or a <a href="/docs/caddyfile/concepts#placeholders">placeholder</a> which will be replaced with the value of the placeholder at the time of the request.
<p></p>
""",
        syntax = """<p></p>
<pre><code>log_append [&lt;matcher&gt;] &lt;key&gt; &lt;value&gt;
</code></pre>
""",
        examples = """<p></p>
Display in the logs the area of the site that the request is being served from, either <code>static</code> or <code>dynamic</code>:
<p></p>
<pre><code>example.com {
	log

	handle /static* {
		log_append area "static"
		respond "Static response!"
	}

	handle {
		log_append area "dynamic"
		reverse_proxy localhost:9000
	}
}
</code></pre>
Display in the logs, which reverse proxy upstream was effectively used (either <code>node1</code>, <code>node2</code> or <code>node3</code>) and
the time spent proxying to the upstream in milliseconds as well as how long it took the proxy upstream to write the response header:
<p></p>
<pre><code>example.com {
	log

	handle {
		reverse_proxy node1:80 node2:80 node3:80 {
			lb_policy random_choose 2 
		}
		log_append upstream_host {rp.upstream.host}
		log_append upstream_duration_ms {rp.upstream.duration_ms}
		log_append upstream_latency_ms {rp.upstream.latency_ms}
	}
}
</code></pre>
""",
    ),
    Directive(
        name = "log_skip",
        description = """<p></p>
Skips access logging for matched requests.
<p></p>
This should be used alongside the <a href="log"><code>log</code> directive</a> to skip logging requests that are not relevant for your needs.
<p></p>
Prior to v2.8.0, this directive was named <code>skip_log</code>, but was renamed for consistency with other directives.
<p></p>
""",
        syntax = """<p></p>
<pre><code>log_skip [&lt;matcher&gt;]
</code></pre>
""",
        examples = """<p></p>
Skip access logging for static files stored in a subpath:
<p></p>
<pre><code>example.com {
	root * /srv

	log
	log_skip /static*

	file_server
}
</code></pre>
Skip access logging for requests matching a pattern; in this case, for files with particular extensions:
<p></p>
<pre><code>@skip path_regexp \.(js|css|png|jpe?g|gif|ico|woff|otf|ttf|eot|svg|txt|pdf|docx?|xlsx?)$
log_skip @skip
</code></pre>
The matcher is not needed if it's found within a route which is already within a matcher. For example with a handle for a file server for a particular subpath:
<p></p>
<pre><code>handle_path /static* {
	root * /srv/static
	log_skip
	file_server
}
</code></pre>
""",
    ),
    Directive(
        name = "log_name",
        description = """<p></p>
Overrides the logger name to use for a request when writing access logs with the <a href="log"><code>log</code> directive</a>.
<p></p>
This directive is useful when you want to log requests to different files based on some condition, such as the request path or method.
<p></p>
More than one logger name can be specified, such that the request's log gets pushed to more than one matching logger.
<p></p>
This is often paired with the <code>log</code> directive's <a href="log#no_hostname"><code>no_hostname</code></a> option, which prevents the logger from being associated with any of the site block's hostnames, so that only requests that set <code>log_name</code> will push logs to that logger.
<p></p>
""",
        syntax = """<p></p>
<pre><code>log_name [&lt;matcher&gt;] &lt;names...&gt;
</code></pre>
""",
        examples = """<p></p>
You may want to log requests to different files, for example you might want to log health checks to a separate file from the main access logs.
<p></p>
Using <code>no_hostname</code> in a <code>log</code> prevents the logger from being associated with any of the site block's hostnames (i.e. <code>localhost</code> here), so that only requests that have <code>log_name</code> set to that logger's name will receive logs.
<p></p>
<pre><code>localhost {
	log {
		output file ./caddy.access.log
	}

	log health_check_log {
		output file ./caddy.access.health.log
		no_hostname
	}

	handle /healthz* {
		log_name health_check_log
		respond "Healthy"
	}

	handle {
		respond "Hello World"
	}
}
</code></pre>
""",
    ),
    Directive(
        name = "map",
        description = """<p></p>
Sets values of custom placeholders switched on an input value.
<p></p>
It compares the source value against the input side of the map, and for one that matches, it applies the output value(s) to each destination. Destinations become placeholder names. Default output values may also be specified for each destination.
<p></p>
Mapped placeholders are not evaluated until they are used, so even for very large mappings, this directive is quite efficient.
<p></p>
""",
        syntax = """<p></p>
<pre><code>map [&lt;matcher&gt;] &lt;source&gt; &lt;destinations...&gt; {
	[~]&lt;input&gt; &lt;outputs...&gt;
	default    &lt;defaults...&gt;
}
</code></pre>
<p>

&lt;source&gt; is the input value to switch on. Usually a placeholder.


&lt;destinations...&gt; are the placeholders to create that hold the output values.


&lt;input&gt; is the input value to match. If prefixed with ~, it is treated as a regular expression.


&lt;outputs...&gt; is one or more output values to store in the associated placeholder. The first output is written to the first destination, the second output to the second destination, etc.
As a special case, the Caddyfile parser treats outputs that are a literal hyphen (-) as null/nil values. This is useful if you want to fall back to a default value for that particular output in the case of the given input, but want to use non-default values for other outputs.
The outputs will be type converted if possible; true and false will be converted to boolean types, and numeric values will be converted to integer or float accordingly. To avoid this conversion, you may wrap the output with quotes and they will stay strings.
The number of outputs for each mapping must not exceed the number of destinations; however, for convenience, there may be fewer outputs than destinations, and any missing outputs will be filled in implicitly.
If a regular expression was used as the input, then the capture groups can be referenced with ${'$'}{group} where group is either the name or number of the capture group in the expression. Capture group 0 is the full regexp match, 1 is the first capture group, 2 is the second capture group, and so on.


&lt;default&gt; specifies the output values to store if no inputs are matched.

</p>
<p></p>
""",
        examples = """<p></p>
The following example demonstrates most aspects of this directive:
<p></p>
<pre><code>map {host}                {my_placeholder}  {magic_number} {
	example.com           "some value"      3
	foo.example.com       "another value"
	~(.*)\.example\.com$  "${'$'}{1} subdomain"  5

	~.*\.net$             -                 7
	~.*\.xyz$             -                 15

	default               "unknown domain"  42
}
</code></pre>
This directive switches on the value of <code>{host}</code>, i.e. the domain name of the request.
<p></p>
<p>
If the request is for example.com, set {my_placeholder} to some value, and {magic_number} to 3.
Else, if the request is for foo.example.com, set {my_placeholder} to another value, and let {magic_number} default to 42.
Else, if the request is for any subdomain of example.com, set {my_placeholder} to a string containing the value of the first regexp capture group, i.e the entire subdomain, and set {magic_number} to 5.
Else, if the request is for any host that ends in .net or .xyz, set only {magic_number} to 7 or 15, respectively. Leave {my_placeholder} unset.
Else (for all other hosts), the default values will apply: {my_placeholder} will be set to unknown domain and {magic_number} will be set to 42.
</p>
<p></p>
""",
    ),
    Directive(
        name = "method",
        description = """<p></p>
Changes the HTTP method on the request.
<p></p>
""",
        syntax = """<p></p>
<pre><code>method [&lt;matcher&gt;] &lt;method&gt;
</code></pre>
<p>
&lt;method&gt; is the HTTP method to change the request to.
</p>
<p></p>
""",
        examples = """<p></p>
Change the method for all requests under <code>/api</code> to <code>POST</code>:
<p></p>
<pre><code>method /api* POST
</code></pre>
""",
    ),
    Directive(
        name = "metrics",
        description = """<p></p>
Configures a Prometheus metrics exposition endpoint so the gathered metrics can
be exposed for scraping. <strong>Metrics must be <a href="/docs/caddyfile/options#metrics">turned on in your global options</a> first.</strong>
<p></p>
Note that a <code>/metrics</code> endpoint is also attached to the <a href="/docs/api">admin API</a>,
which is not configurable, and is not available when the admin API is disabled.
<p></p>
This endpoint will return metrics in the <a href="https://prometheus.io/docs/instrumenting/exposition_formats/#text-based-format">Prometheus exposition format</a>
or, if negotiated, in the <a href="https://pkg.go.dev/github.com/prometheus/client_golang@v1.9.0/prometheus/promhttp#HandlerOpts">OpenMetrics exposition format</a>
(<code>application/openmetrics-text</code>).
<p></p>
See also <a href="/docs/metrics">Monitoring Caddy with Prometheus metrics</a>.
<p></p>
""",
        syntax = """<p></p>
<pre><code>metrics [&lt;matcher&gt;] {
	disable_openmetrics
}
</code></pre>
<p>
disable_openmetrics disables OpenMetrics negotiation. Usually not
necessary except when needing to work around parsing bugs.
</p>
<p></p>
""",
        examples = """<p></p>
Expose metrics at the default <code>/metrics</code> path:
<p></p>
<pre><code>metrics /metrics
</code></pre>
Expose metrics at another path:
<p></p>
<pre><code>metrics /foo/bar/baz
</code></pre>
Serve metrics at a separate subdomain:
<p></p>
<pre><code>metrics.example.com {
	metrics
}
</code></pre>
Disable OpenMetrics negotiation:
<p></p>
<pre><code>metrics /metrics {
	disable_openmetrics
}
</code></pre>
""",
    ),
    Directive(
        name = "php_fastcgi",
        description = """<p></p>
An opinionated directive that proxies requests to a PHP FastCGI server such as php-fpm.
<p></p>
<p>
Syntax
Expanded Form

Explanation


Examples
</p>
<p></p>
Caddy's <a href="reverse_proxy"><code>reverse_proxy</code></a> is capable of serving any FastCGI application, but this directive is tailored specifically for PHP apps. This directive is a convenient shortcut, replacing a <a href="#expanded-form">longer configuration</a>.
<p></p>
It expects that any <code>index.php</code> at the site root acts as a router. If that is not desirable, either reconfigure the <a href="#try_files"><code>try_files</code> subdirective</a> to modify the default rewrite behaviour, or take the <a href="#expanded-form">expanded form</a> as a basis and customize it to your needs.
<p></p>
In addition to the subdirectives listed below, this directive also supports all the subdirectives of <a href="reverse_proxy#syntax"><code>reverse_proxy</code></a>. For example, you may enable load balancing and health checks.
<p></p>
<strong>Most modern PHP apps work fine without extra subdirectives or customization.</strong> Subdirectives are usually only used in certain edge cases or with legacy PHP apps.
<p></p>
""",
        syntax = """<p></p>
<pre><code>php_fastcgi [&lt;matcher&gt;] &lt;php-fpm_gateways...&gt; {
	root &lt;path&gt;
	split &lt;substrings...&gt;
	index &lt;filename&gt;|off
	try_files &lt;files...&gt;
	env [&lt;key&gt; &lt;value&gt;]
	resolve_root_symlink
	capture_stderr
	dial_timeout  &lt;duration&gt;
	read_timeout  &lt;duration&gt;
	write_timeout &lt;duration&gt;

	&lt;any other reverse_proxy subdirectives...&gt;
}
</code></pre>
<p>

&lt;php-fpm_gateways...&gt; are the addresses of the FastCGI servers. Typically, either a TCP socket, or a unix socket file.


root  sets the root folder to the site. It's recommended to always use the root directive in conjunction with php_fastcgi, but overriding this can be useful when your PHP-FPM upstream is using a different root than Caddy (see an example). Defaults to the value of the root directive if used, otherwise defaults to Caddy's current working directory.


split  sets the substrings for splitting the URI into two parts. The first matching substring will be used to split the "path info" from the path. The first piece is suffixed with the matching substring and will be assumed as the actual resource (CGI script) name. The second piece will be set to PATH_INFO for the CGI script to use. Default: .php


index  specifies the filename to treat as the directory index file. This affects the file matcher in the expanded form. Default: index.php. Can be set to off to disable rewrite fallback to index.php when a matching file is not found.


try_files  specifies an override for the default try-files rewrite. See the try_files directive for details. Default: {path} {path}/index.php index.php.


env  sets an extra environment variable to the given value. Can be specified more than once for multiple environment variables. By default, all the relevant FastCGI environment variables are already set (including HTTP headers) but you may add or override variables as needed.


resolve_root_symlink  when the root directory is a symbolic link (symlink), this enables resolving it to its actual value. This is sometimes used as a deployment strategy, by simply swapping the symlink to point to the new version in another directory. Disabled by default to avoid repeated system calls.


capture_stderr  enables capturing and logging of any messages sent by the upstream fastcgi server on stderr. Logging is done at WARN level by default. If the response has a 4xx or 5xx status, then the ERROR level will be used instead. By default, stderr is ignored.


dial_timeout  is a duration value that sets how long to wait when connecting to the upstream socket. Default: 3s.


read_timeout  is a duration value that sets how long to wait when reading from the FastCGI upstream. Default: no timeout.


write_timeout  is a duration value that sets how long to wait when sending to the FastCGI upstream. Default: no timeout.

</p>
<p></p>
Since this directive is an opinionated wrapper over a reverse proxy, you can use any of <a href="reverse_proxy#syntax"><code>reverse_proxy</code></a>'s subdirectives to customize it.
<p></p>
""",
        examples = """<p></p>
Proxy all PHP requests to a FastCGI responder listening at <code>127.0.0.1:9000</code>:
<p></p>
<pre><code>php_fastcgi 127.0.0.1:9000
</code></pre>
Same, but only for requests under <code>/blog/</code>:
<p></p>
<pre><code>php_fastcgi /blog/* localhost:9000
</code></pre>
When using PHP-FPM listening via a unix socket:
<p></p>
<pre><code>php_fastcgi unix//run/php/php8.2-fpm.sock
</code></pre>
The <a href="root"><code>root</code> directive</a> is almost always used to specify the directory containing the PHP scripts, and the <a href="file_server"><code>file_server</code> directive</a> to serve static files:
<p></p>
<pre><code>example.com {
	root * /var/www/html/public
	php_fastcgi 127.0.0.1:9000
	file_server
}
</code></pre>
<span id="docker"> When serving multiple PHP apps with Caddy, your webroot for each app must be different so that Caddy can read and serve your static files separately and detect if PHP files exist.</span>
<p></p>
If you're using Docker, often your PHP-FPM containers will have the files mounted at the same root. In that case, the solution is to mount the files to your Caddy container in different directories, then use the <a href="#root"><code>root</code> subdirective</a> to set the root for each container:
<p></p>
<pre><code>app1.example.com {
	root * /srv/app1/public
	php_fastcgi app1:9000 {
		root /var/www/html/public
	}
	file_server
}

app2.example.com {
	root * /srv/app2/public
	php_fastcgi app2:9000 {
		root /var/www/html/public
	}
	file_server
}
</code></pre>
For a PHP site which does not use <code>index.php</code> as an entrypoint, you may fallback to emitting a <code>404</code> error instead. The error may be caught and handled with the <a href="handle_errors"><code>handle_errors</code> directive</a>:
<p></p>
<pre><code>example.com {
	php_fastcgi localhost:9000 {
		try_files {path} {path}/index.php =404
	}

	handle_errors {
		respond "{err.status_code} {err.status_text}"
	}
}
</code></pre>
""",
    ),
    Directive(
        name = "push",
        description = """<p></p>
Configures the server to pre-emptively send resources to the client using HTTP/2 server push.
<p></p>
Resources can be linked for server push by specifying the Link header(s) of the response. This directive will automatically push resources described by upstream Link headers in these formats:
<p></p>
<p>
&lt;resource&gt;; as=script
&lt;resource&gt;; as=script,&lt;resource&gt;; as=style
&lt;resource&gt;; nopush
&lt;resource&gt;;&lt;resource2&gt;;...
</p>
<p></p>
where <code>&lt;resource&gt;</code> begins with a forward slash <code>/</code> (i.e. is a URI path with the same host). Only same-host resources can be pushed. If a linked resource is external or if it has the <code>nopush</code> attribute, it will not be pushed.
<p></p>
By default, push requests will include some headers deemed safe to copy from the original request:
<p></p>
<p>
Accept-Encoding
Accept-Language
Accept
Cache-Control
User-Agent
</p>
<p></p>
as it is assumed many requests would fail without these headers; these do not need to be configured manually.
<p></p>
Push requests are virtualized internally, so they are very lightweight.
<p></p>
""",
        syntax = """<p></p>
<pre><code>push [&lt;matcher&gt;] [&lt;resource&gt;] {
	[GET|HEAD] &lt;resource&gt;
	headers {
		[+]&lt;field&gt; [&lt;value|regexp&gt; [&lt;replacement&gt;]]
		-&lt;field&gt;
	}
}
</code></pre>
<p>
&lt;resource&gt; is the target URI path to push. If used within the block, may optionally be preceeded by the method (GET or POST; GET is default).
&lt;headers&gt; manipulates the headers of the push request using the same syntax as the header directive. Some headers are carried over by default and do not need to be explicitly configured (see above).
</p>
<p></p>
""",
        examples = """<p></p>
Push any resources described by <code>Link</code> headers in the response:
<p></p>
<pre><code>push
</code></pre>
Same, but also push <code>/resources/style.css</code> for all requests:
<p></p>
<pre><code>push * /resources/style.css
</code></pre>
Push <code>/foo.jpg</code> only when <code>/foo.html</code> is requested by the client:
<p></p>
<pre><code>push /foo.html /foo.jpg
</code></pre>
""",
    ),
    Directive(
        name = "redir",
        description = """<p></p>
Issues an HTTP redirect to the client.
<p></p>
This directive implies that a matched request is to be rejected as-is, and the client should try again at a different URL. For that reason, its <a href="/docs/caddyfile/directives#directive-order">directive order</a> is very early.
<p></p>
""",
        syntax = """<p></p>
<pre><code>redir [&lt;matcher&gt;] &lt;to&gt; [&lt;code&gt;]
</code></pre>
<p>

&lt;to&gt; is the target location. Becomes the response's Location header .


&lt;code&gt; is the HTTP status code to use for the redirect. Can be:


A positive integer in the 3xx range, or 401


temporary for a temporary redirect (302, this is the default)


permanent for a permanent redirect (301)


html to use an HTML document to perform the redirect (useful for redirecting browsers but not API clients)


A placeholder with a status code value



</p>
<p></p>
""",
        examples = """<p></p>
Redirect all requests to <code>https://example.com</code>:
<p></p>
<pre><code>www.example.com {
	redir https://example.com
}
</code></pre>
Same, but preserve the existing URI by appending the <a href="/docs/caddyfile/concepts#placeholders"><code>{uri}</code> placeholder</a>:
<p></p>
<pre><code>www.example.com {
	redir https://example.com{uri}
}
</code></pre>
Same, but permanent:
<p></p>
<pre><code>www.example.com {
	redir https://example.com{uri} permanent
}
</code></pre>
Redirect your old <code>/about-us</code> page to your new <code>/about</code> page:
<p></p>
<pre><code>example.com {
	redir /about-us /about
	reverse_proxy localhost:9000
}
</code></pre>
""",
    ),
    Directive(
        name = "request_body",
        description = """<p></p>
Manipulates or sets restrictions on the bodies of incoming requests.
<p></p>
""",
        syntax = """<p></p>
<pre><code>request_body [&lt;matcher&gt;] {
	max_size &lt;value&gt;
}
</code></pre>
<p>
max_size is the maximum size in bytes allowed for the request body. It accepts all size values supported by go-humanize. Reads of more bytes will return an error with HTTP status 413.
</p>
<p></p>
""",
        examples = """<p></p>
Limit request body sizes to 10 megabytes:
<p></p>
<pre><code>example.com {
	request_body {
		max_size 10MB
	}
	reverse_proxy localhost:8080
}
</code></pre>
""",
    ),
    Directive(
        name = "request_header",
        description = """<p></p>
Manipulates HTTP header fields on the request. It can set, add, and delete header values, or perform replacements using regular expressions.
<p></p>
If you intend to manipulate headers for proxying, use the <a href="/docs/caddyfile/directives/reverse_proxy#header_up"><code>header_up</code> subdirective</a> of <code>reverse_proxy</code> instead, as those manipulations are proxy-aware.
<p></p>
To manipulate HTTP response headers, you may use the <a href="header"><code>header</code></a> directive.
<p></p>
""",
        syntax = """<p></p>
<pre><code>request_header [&lt;matcher&gt;] [[+|-]&lt;field&gt; [&lt;value&gt;|&lt;find&gt;] [&lt;replace&gt;]]
</code></pre>
<p>

&lt;field&gt; is the name of the header field.
With no prefix, the field is set (overwritten).
Prefix with + to add the field instead of overwriting (setting) the field if it already exists; header fields can appear more than once in a request.
Prefix with - to delete the field. The field may use prefix or suffix * wildcards to delete all matching fields.


&lt;value&gt; is the header field value, if adding or setting a field.


&lt;find&gt; is the substring or regular expression to search for.


&lt;replace&gt; is the replacement value; required if performing a search-and-replace.

</p>
<p></p>
""",
        examples = """<p></p>
Remove the Referer header from the request:
<p></p>
<pre><code>request_header -Referer
</code></pre>
Delete all headers containing an underscore from the request:
<p></p>
<pre><code>request_header -*_*
</code></pre>
""",
    ),
    Directive(
        name = "respond",
        description = """<p></p>
Writes a hard-coded/static response to the client.
<p></p>
If the body is non-empty, this directive sets the <code>Content-Type</code> header if it is not already set. The default value is <code>text/plain; utf-8</code> unless the body is a valid JSON object or array, in which case it is set to <code>application/json</code>. For all other types of content, set the proper Content-Type explicitly using the <a href="/docs/caddyfile/directives/header"><code>header</code> directive</a>.
<p></p>
""",
        syntax = """<p></p>
<pre><code>respond [&lt;matcher&gt;] &lt;status&gt;|&lt;body&gt; [&lt;status&gt;] {
	body &lt;text&gt;
	close
}
</code></pre>
<p>

&lt;status&gt; is the HTTP status code to write.
If 103 (Early Hints), the response will be written without a body and the handler chain will continue. (HTTP 1xx responses are informational, not final.)
Default: 200


&lt;body&gt; is the response body to write.


body is an alternate way to provide a body; convenient if it is multiple lines.


close will close the client's connection to the server after writing the response.

</p>
<p></p>
To clarify, the first non-matcher argument can be either a 3-digit status code or a response body string. If it is a body, the next argument can be the status code.
<p></p>
<p>
Responding with an error status code is different than returning an error in the handler chain, which invokes error handlers internally.
</p>
<p></p>
""",
        examples = """<p></p>
Write an empty 200 status with an empty body to all health checks, and a simple response body to all other requests:
<p></p>
<pre><code>example.com {
	respond /health-check 200
	respond "Hello, world!"
}
</code></pre>
Write an error response and close the connection:
<p></p>
<p>
You might prefer to use the error directive instead, which triggers an error that can be handled with the handle_errors directive.
</p>
<p></p>
<pre><code>example.com {
	respond /secret/* "Access denied" 403 {
		close
	}
}
</code></pre>
Write an HTML response, using <a href="/docs/caddyfile/concepts#heredocs">heredoc syntax</a> to control whitespace, and also setting the <code>Content-Type</code> header to match the response body:
<p></p>
<pre><code>example.com {
	header Content-Type text/html
	respond &lt;&lt;HTML
		&lt;html&gt;
			&lt;head&gt;&lt;title&gt;Foo&lt;/title&gt;&lt;/head&gt;
			&lt;body&gt;Foo&lt;/body&gt;
		&lt;/html&gt;
		HTML 200
}
</code></pre>
""",
    ),
    Directive(
        name = "reverse_proxy",
        description = """<p></p>
Proxies requests to one or more backends with configurable transport, load balancing, health checking, request manipulation, and buffering options.
<p></p>
<p>
Syntax
Upstreams

Upstream addresses
Dynamic upstreams

SRV
A/AAAA
Multi




Load balancing

Active health checks
Passive health checks
Events


Streaming
Headers
Rewrites
Transports

The http transport
The fastcgi transport


Intercepting responses
Examples
</p>
<p></p>
""",
        syntax = """<p></p>
<pre><code>reverse_proxy [&lt;matcher&gt;] [&lt;upstreams...&gt;] {
	# backends
	to      &lt;upstreams...&gt;
	dynamic &lt;module&gt; ...

	# load balancing
	lb_policy       &lt;name&gt; [&lt;options...&gt;]
	lb_retries      &lt;retries&gt;
	lb_try_duration &lt;duration&gt;
	lb_try_interval &lt;interval&gt;
	lb_retry_match  &lt;request-matcher&gt;

	# active health checking
	health_uri      &lt;uri&gt;
	health_port     &lt;port&gt;
	health_interval &lt;interval&gt;
	health_passes   &lt;num&gt;
	health_fails	&lt;num&gt;
	health_timeout  &lt;duration&gt;
	health_status   &lt;status&gt;
	health_body     &lt;regexp&gt;
	health_follow_redirects
	health_headers {
		&lt;field&gt; [&lt;values...&gt;]
	}

	# passive health checking
	fail_duration     &lt;duration&gt;
	max_fails         &lt;num&gt;
	unhealthy_status  &lt;status&gt;
	unhealthy_latency &lt;duration&gt;
	unhealthy_request_count &lt;num&gt;

	# streaming
	flush_interval     &lt;duration&gt;
	request_buffers    &lt;size&gt;
	response_buffers   &lt;size&gt;
	stream_timeout     &lt;duration&gt;
	stream_close_delay &lt;duration&gt;

	# request/header manipulation
	trusted_proxies [private_ranges] &lt;ranges...&gt;
	header_up   [+|-]&lt;field&gt; [&lt;value|regexp&gt; [&lt;replacement&gt;]]
	header_down [+|-]&lt;field&gt; [&lt;value|regexp&gt; [&lt;replacement&gt;]]
	method &lt;method&gt;
	rewrite &lt;to&gt;

	# round trip
	transport &lt;name&gt; {
		...
	}

	# optionally intercept responses from upstream
	@name {
		status &lt;code...&gt;
		header &lt;field&gt; [&lt;value&gt;]
	}
	replace_status [&lt;matcher&gt;] &lt;status_code&gt;
	handle_response [&lt;matcher&gt;] {
		&lt;directives...&gt;

		# special directives only available in handle_response
		copy_response [&lt;matcher&gt;] [&lt;status&gt;] {
			status &lt;status&gt;
		}
		copy_response_headers [&lt;matcher&gt;] {
			include &lt;fields...&gt;
			exclude &lt;fields...&gt;
		}
	}
}
</code></pre>
""",
        examples = """<p></p>
Reverse proxy all requests to a local backend:
<p></p>
<pre><code>example.com {
	reverse_proxy localhost:9005
}
</code></pre>
<a href="#load-balancing">Load-balance</a> all requests <a href="#upstreams">between 3 backends</a>:
<p></p>
<pre><code>example.com {
	reverse_proxy node1:80 node2:80 node3:80
}
</code></pre>
Same, but only requests within <code>/api</code>, and sticky by using the <a href="#lb_policy"><code>cookie</code> policy</a>:
<p></p>
<pre><code>example.com {
	reverse_proxy /api/* node1:80 node2:80 node3:80 {
		lb_policy cookie api_sticky
	}
}
</code></pre>
Using <a href="#active-health-checks">active health checks</a> to determine which backends are healthy, and enabling <a href="#lb_try_duration">retries</a> on failed connections, holding the request until a healthy backend is found:
<p></p>
<pre><code>example.com {
	reverse_proxy node1:80 node2:80 node3:80 {
		health_uri /healthz
		lb_try_duration 5s
	}
}
</code></pre>
Configure some <a href="#transports">transport options</a>:
<p></p>
<pre><code>example.com {
	reverse_proxy localhost:8080 {
		transport http {
			dial_timeout 2s
			response_header_timeout 30s
		}
	}
}
</code></pre>
Reverse proxy to an <a href="#https">HTTPS upstream</a>:
<p></p>
<pre><code>example.com {
	reverse_proxy https://example.com {
		header_up Host {upstream_hostport}
	}
}
</code></pre>
Reverse proxy to an HTTPS upstream, but <a href="#tls_insecure_skip_verify">⚠️ disable TLS verification</a>. This is NOT RECOMMENDED, since it disables all security checks that HTTPS offers; proxying over HTTP in private networks is preferred if possible, because it avoids the false sense of security:
<p></p>
<pre><code>example.com {
	reverse_proxy 10.0.0.1:443 {
		transport http {
			tls_insecure_skip_verify
		}
	}
}
</code></pre>
Instead you may establish trust with the upstream by explicitly <a href="#tls_trusted_ca_certs">trusting the upstream's certificate</a>, and (optionally) setting TLS-SNI to match the hostname in the upstream's certificate:
<p></p>
<pre><code>example.com {
	reverse_proxy 10.0.0.1:443 {
		transport http {
			tls_trusted_ca_certs /path/to/cert.pem
			tls_server_name app.example.com
		}
	}
}
</code></pre>
<a href="handle_path">Strip a path prefix</a> before proxying; but be aware of the <a href="https://caddy.community/t/the-subfolder-problem-or-why-cant-i-reverse-proxy-my-app-into-a-subfolder/8575">subfolder problem <img src="/old/resources/images/external-link.svg" class="external-link"></a>:
<p></p>
<pre><code>example.com {
	handle_path /prefix/* {
		reverse_proxy localhost:9000
	}
}
</code></pre>
Replace a path prefix before proxying, using a <a href="/docs/caddyfile/directives/rewrite"><code>rewrite</code></a>:
<p></p>
<pre><code>example.com {
	handle_path /old-prefix/* {
		rewrite * /new-prefix{path}
		reverse_proxy localhost:9000
	}
}
</code></pre>
<code>X-Accel-Redirect</code> support, i.e. serving static files as requested, by <a href="#intercepting-responses">intercepting the response</a>:
<p></p>
<pre><code>example.com {
	reverse_proxy localhost:8080 {
		@accel header X-Accel-Redirect *
		handle_response @accel {
			root    * /path/to/private/files
			rewrite * {rp.header.X-Accel-Redirect}
			method  * GET
			file_server
		}
	}
}
</code></pre>
Custom error page for errors from upstream, by <a href="#intercepting-responses">intercepting error responses</a> by status code:
<p></p>
<pre><code>example.com {
	reverse_proxy localhost:8080 {
		@error status 500 503
		handle_response @error {
			root    * /path/to/error/pages
			rewrite * /{rp.status_code}.html
			file_server
		}
	}
}
</code></pre>
Get backends <a href="#dynamic-upstreams">dynamically</a> from <a href="#aaaaa"><code>A</code>/<code>AAAA</code> record</a> DNS queries:
<p></p>
<pre><code>example.com {
	reverse_proxy {
		dynamic a example.com 9000
	}
}
</code></pre>
Get backends <a href="#dynamic-upstreams">dynamically</a> from <a href="#srv"><code>SRV</code> record</a> DNS queries:
<p></p>
<pre><code>example.com {
	reverse_proxy {
		dynamic srv _api._tcp.example.com
	}
}
</code></pre>
""",
    ),
    Directive(
        name = "rewrite",
        description = """<p></p>
Rewrites the request URI internally.
<p></p>
A rewrite changes some or all of the request URI. Note that the URI does not include scheme or authority (host &amp; port), and clients typically do not send fragments. Thus, this directive is mostly used for <strong>path</strong> and <strong>query</strong> string manipulation.
<p></p>
The <code>rewrite</code> directive implies the intent to accept the request, but with modifications.
<p></p>
It is mutually exclusive to other <code>rewrite</code> directives in the same block, so it is safe to define rewrites that would otherwise cascade into each other as only the first matching rewrite will be executed.
<p></p>
A <a href="/docs/caddyfile/matchers">request matcher</a> that matches a request before the <code>rewrite</code> might not match the same request after the <code>rewrite</code>. If you want your <code>rewrite</code> to share a route with other handlers, use the <a href="route"><code>route</code></a> or <a href="handle"><code>handle</code></a> directives.
<p></p>
""",
        syntax = """<p></p>
<pre><code>rewrite [&lt;matcher&gt;] &lt;to&gt;
</code></pre>
<p>
&lt;to&gt; is the URI to rewrite the request to. Only the components of the URI (path or query string) that are specified in the rewrite will be operated on. The URI path is any substring that comes before ?. If ? is omitted, then the whole token is considered to be the path.
</p>
<p></p>
Prior to v2.8.0, the <code>&lt;to&gt;</code> argument could be confused by the parser for a <a href="/docs/caddyfile/matchers#syntax">matcher token</a> if it began with <code>/</code>, so it was necessary to specify a wildcard matcher token (<code>*</code>).
<p></p>
""",
        examples = """<p></p>
Rewrite all requests to <code>index.html</code>, leaving any query string unchanged:
<p></p>
<pre><code>example.com {
	rewrite * /index.html
}
</code></pre>
<p>
Note that prior to v2.8.0, a wildcard matcher was required here because the first argument is ambiguous with a path matcher, i.e. rewrite * /foo, but it can now be simplified to rewrite /foo.
</p>
<p></p>
Prefixing all requests with <code>/api</code>, preserving the rest of the URI, then reverse proxying to an app:
<p></p>
<pre><code>api.example.com {
	rewrite * /api{uri}
	reverse_proxy localhost:8080
}
</code></pre>
Replace the query string on API requests with <code>a=b</code>, leaving the path unchanged:
<p></p>
<pre><code>example.com {
	rewrite * ?a=b
}
</code></pre>
For only requests to <code>/api/</code>, preserve the existing query string and add a key-value pair:
<p></p>
<pre><code>example.com {
	rewrite /api/* ?{query}&amp;a=b
}
</code></pre>
Change both the path and query string, preserving the original query string while adding the original path as the <code>p</code> parameter:
<p></p>
<pre><code>example.com {
	rewrite * /index.php?{query}&amp;p={path}
}
</code></pre>
""",
    ),
    Directive(
        name = "root",
        description = """<p></p>
Sets the root path of the site, used by various matchers and directives that access the file system. If unset, the default site root is the current working directory.
<p></p>
Specifically, this directive sets the <code>{http.vars.root}</code> placeholder. It is mutually exclusive to other <code>root</code> directives in the same block, so it is safe to define multiple roots with matchers that intersect: they will not cascade and overwrite each other.
<p></p>
This directive does not automatically enable serving static files, so it is often used in conjunction with the <a href="file_server"><code>file_server</code> directive</a> or the <a href="php_fastcgi"><code>php_fastcgi</code> directive</a>.
<p></p>
""",
        syntax = """<p></p>
<pre><code>root [&lt;matcher&gt;] &lt;path&gt;
</code></pre>
<p>
&lt;path&gt; is the path to use for the site root.
</p>
<p></p>
Prior to v2.8.0, the <code>&lt;path&gt;</code> argument could be confused by the parser for a <a href="/docs/caddyfile/matchers#syntax">matcher token</a> if it began with <code>/</code>, so it was necessary to specify a wildcard matcher token (<code>*</code>).
<p></p>
""",
        examples = """<p></p>
Set the site root to <code>/home/bob/public_html</code> (assumes Caddy is running as the user <code>bob</code>):
<p></p>
<p>
If you're running Caddy as a systemd service, reading files from /home will not work, because the caddy user does not have "executable" permission on the /home directory (necessary for traversal). It's recommended that you place your files in /srv or /var/www/html instead.
</p>
<p></p>
<pre><code>root * /home/bob/public_html
</code></pre>
<p>
Note that prior to v2.8.0, a wildcard matcher was required here because the first argument is ambiguous with a path matcher, i.e. root * /srv, but it can now be simplified to root /srv.
</p>
<p></p>
Set the site root to <code>public_html</code> (relative to current working directory) for all requests:
<p></p>
<pre><code>root public_html
</code></pre>
Change the site root only for requests in <code>/foo/*</code>:
<p></p>
<pre><code>root /foo/* /home/user/public_html/foo
</code></pre>
The <code>root</code> directive is commonly paired with <a href="file_server"><code>file_server</code></a> to serve static files and/or with <a href="php_fastcgi"><code>php_fastcgi</code></a> to serve a PHP site:
<p></p>
<pre><code>example.com {
	root * /srv
	file_server
}
</code></pre>
""",
    ),
    Directive(
        name = "route",
        description = """<p></p>
Evaluates a group of directives literally and as a single unit.
<p></p>
Directives contained in a route block will not be <a href="/docs/caddyfile/directives#directive-order">reordered internally</a>. Only HTTP handler directives (directives which add handlers or middleware to the chain) can be used in a route block.
<p></p>
This directive is a special case in that its subdirectives are also regular directives.
<p></p>
""",
        syntax = """<p></p>
<pre><code>route [&lt;matcher&gt;] {
	&lt;directives...&gt;
}
</code></pre>
<p>
&lt;directives...&gt; is a list of directives or directive blocks, one per line, just like outside of a route block; except these directives will not be reordered. Only HTTP handler directives can be used.
</p>
<p></p>
""",
        examples = """<p></p>
Proxy requests to <code>/api</code> as-is, and rewrite all other requests based on whether they match a file on disk, otherwise <code>/index.html</code>. Then that file is served.
<p></p>
Since <a href="try_files"><code>try_files</code></a> has a higher directive order than <a href="reverse_proxy"><code>reverse_proxy</code></a>, then it would normally get sorted higher and run first; this would cause the API requests to all get rewritten to <code>/index.html</code> and fail to match <code>/api*</code>, so none of them would get proxied and instead would result in a <code>404</code> from <a href="file_server"><code>file_server</code></a>. Wrapping it all in a <code>route</code> ensures that <code>reverse_proxy</code> always runs first, before the request is rewritten.
<p></p>
<pre><code>example.com {
	root * /srv
	route {
		reverse_proxy /api* localhost:9000

		try_files {path} /index.html
		file_server
	}
}
</code></pre>
<p>
This is not the only solution to this problem. You could also use a pair of handle blocks, with the first matching /api* to reverse_proxy, and the second acting as a fallback and serving the files. See this example of an SPA.
</p>
<p></p>
""",
    ),
    Directive(
        name = "templates",
        description = """<p></p>
Executes the response body as a <a href="/docs/modules/http.handlers.templates">template</a> document. Templates provide functional primitives for making simple dynamic pages. Features include HTTP subrequests, HTML file includes, Markdown rendering, JSON parsing, basic data structures, randomness, time, and more.
<p></p>
""",
        syntax = """<p></p>
<pre><code>templates [&lt;matcher&gt;] {
	mime    &lt;types...&gt;
	between &lt;open_delim&gt; &lt;close_delim&gt;
	root    &lt;path&gt;
}
</code></pre>
<p>

mime are the MIME types the templates middleware will act on; any responses that do not have a qualifying Content-Type will not be evaluated as templates.
Default: text/html text/plain.


between are the opening and closing delimiters for template actions. You can change them if they interfere with the rest of your document.
Default: {{ }}.


root is the site root, when using functions that access the file system.
Defaults to the site root set by the root directive, or the current working directory if not set.

</p>
<p></p>
Documentation for the built-in template functions can be found in <a href="/docs/modules/http.handlers.templates#docs">templates module</a>.
<p></p>
""",
        examples = """<p></p>
For a complete example of a site using templates to serve markdown, take a look at the source for <a href="https://github.com/caddyserver/website">this very website</a>! Specifically, take a look at the <a href="https://github.com/caddyserver/website/blob/master/Caddyfile"><code>Caddyfile</code></a> and <a href="https://github.com/caddyserver/website/blob/master/src/docs/index.html"><code>src/docs/index.html</code></a>.
<p></p>
Enable templates for a static site:
<p></p>
<pre><code>example.com {
	root * /srv
	templates
	file_server
}
</code></pre>
To serve a simple static response using a template, make sure to set <code>Content-Type</code>:
<p></p>
<pre><code>example.com {
	header Content-Type text/plain
	templates
	respond `Current year is: {{now | date "2006"}}`
}
</code></pre>
""",
    ),
    Directive(
        name = "tls",
        description = """<p></p>
Configures TLS for the site.
<p></p>
<strong>Caddy's default TLS settings are secure. Only change these settings if you have a good reason and understand the implications.</strong> The most common use of this directive will be to specify an ACME account email address, change the ACME CA endpoint, or to provide your own certificates.
<p></p>
Compatibility note: Due to its sensitive nature as a security protocol, deliberate adjustments to TLS defaults may be made in new minor or patch releases. Old or broken TLS versions, ciphers, features, etc. may be removed at any time. If your deployment is extremely sensitive to changes, you should explicitly specify those values which must remain constant, and be vigilant about upgrades. In almost every case, we recommend using the default settings.
<p></p>
""",
        syntax = """<p></p>
<pre><code>tls [internal|&lt;email&gt;] | [&lt;cert_file&gt; &lt;key_file&gt;] {
	protocols &lt;min&gt; [&lt;max&gt;]
	ciphers   &lt;cipher_suites...&gt;
	curves    &lt;curves...&gt;
	alpn      &lt;values...&gt;
	load      &lt;paths...&gt;
	ca        &lt;ca_dir_url&gt;
	ca_root   &lt;pem_file&gt;
	key_type  ed25519|p256|p384|rsa2048|rsa4096
	dns       &lt;provider_name&gt; [&lt;params...&gt;]
	propagation_timeout &lt;duration&gt;
	propagation_delay   &lt;duration&gt;
	dns_ttl             &lt;duration&gt;
	dns_challenge_override_domain &lt;domain&gt;
	resolvers &lt;dns_servers...&gt;
	eab       &lt;key_id&gt; &lt;mac_key&gt;
	on_demand
	reuse_private_keys
	client_auth {
		mode                   [request|require|verify_if_given|require_and_verify]
		trust_pool             &lt;module&gt;
		verifier 			   &lt;module&gt;
	}
	issuer          &lt;issuer_name&gt;  [&lt;params...&gt;]
	get_certificate &lt;manager_name&gt; [&lt;params...&gt;]
	insecure_secrets_log &lt;log_file&gt;
}
</code></pre>
<p>

internal means to use Caddy's internal, locally-trusted CA to produce certificates for this site. To further configure the internal issuer, use the issuer subdirective.


&lt;email&gt; is the email address to use for the ACME account managing the site's certificates. You may prefer to use the email global option instead, to configure this for all your sites at once.

</p>
<p></p>
<p>
Keep in mind that Let's Encrypt may send you emails about your certificate nearing expiry, but this may be misleading because Caddy may have chosen to use a different issuer (e.g. ZeroSSL) when renewing. Check your logs and/or the certificate itself (in your browser for example) to see which issuer was used, and that its expiry is still valid; if so, you may safely ignore the email from Let's Encrypt.
</p>
<p></p>
<p>

&lt;cert_file&gt; and &lt;key_file&gt; are the paths to the certificate and private key PEM files. Specifying just one is invalid.


protocols  specifies the minimum and maximum protocol versions. DO NOT change these unless you know what you're doing. Configuring this is rarely necessary, because Caddy will always use modern defaults.
Default min: tls1.2, Default max: tls1.3


ciphers  specifies the list of cipher suite names in descending preference order. DO NOT change these unless you know what you're doing. Note that cipher suites are not customizable for TLS 1.3; and not all TLS 1.2 ciphers are enabled by default. The supported names are (in order of preference by the Go stdlib):

TLS_AES_128_GCM_SHA256
TLS_CHACHA20_POLY1305_SHA256
TLS_AES_256_GCM_SHA384
TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384
TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256
TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256
TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA
TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA
TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA
TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA
TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA



curves  specifies the list of EC curves to support. It is recommended to not change these. Supported values are:

x25519
secp256r1
secp384r1
secp521r1



alpn  is the list of values to advertise in the ALPN extension  of the TLS handshake.


load  specifies a list of folders from which to load PEM files that are certificate+key bundles.


ca  changes the ACME CA endpoint. This is most often used to set Let's Encrypt's staging endpoint  when testing, or an internal ACME server. (To change this value for the whole Caddyfile, use the acme_ca global option instead.)


ca_root  specifies a PEM file that contains a trusted root certificate for the ACME CA endpoint, if not in the system trust store.


key_type  is the type of key to use when generating CSRs. Only set this if you have a specific requirement.


dns  enables the DNS challenge using the specified provider plugin, which must be plugged in from one of the caddy-dns  repositories. Each provider plugin may have their own syntax following their name; refer to their docs for details. Maintaining support for each DNS provider is a community effort. Learn how to enable the DNS challenge for your provider at our wiki.


propagation_timeout  is a duration value that sets the maximum time to wait for the DNS TXT records to appear when using the DNS challenge. Set to -1 to disable propagation checks. Default 2 minutes.


propagation_delay  is a duration value that sets how long to wait before starting DNS TXT records propagation checks when using the DNS challenge. Default 0 (no wait).


dns_ttl  is a duration value that sets the TTL of the TXT record used for the DNS challenge. Rarely needed.


dns_challenge_override_domain  overrides the domain to use for the DNS challenge. This is to delegate the challenge to a different domain.
You may want to use this if your primary domain's DNS provider does not have a DNS plugin  available. You can instead add a CNAME record with subdomain _acme-challenge to your primary domain, pointing to a secondary domain for which you do have a plugin. This option does not require special support from the plugin.
When ACME issuers try to solve the DNS challenge for your primary domain, they will then follow the CNAME to your secondary domain to find the TXT record.
Note: Use full canonical name from the CNAME record as value here - _acme-challenge subdomain won't be prepended automatically.


resolvers  customizes the DNS resolvers used when performing the DNS challenge; these take precedence over system resolvers or any default ones. If set here, the resolvers will propagate to all configured certificate issuers.
This is typically a list of IP addresses. For example, to use Google Public DNS :
resolvers 8.8.8.8 8.8.4.4


eab  configures ACME external account binding (EAB) for this site, using the key ID and MAC key provided by your CA.


on_demand  enables On-Demand TLS for the hostnames given in the site block's address(es). Security warning: Doing so in production is insecure unless you also configure the on_demand_tls global option to mitigate abuse.


reuse_private_keys  enables reuse of private keys when renewing certificates. By default, a new key is created for every new certificate to mitigate pinning and reduce the scope of key compromise. Key pinning is against industry best practices. This option is not recommended unless you have a specific reason to use it; this may be subject to removal in a future version.


client_auth  enables and configures TLS client authentication:


mode  is the mode for authenticating the client. Allowed values are:



Mode
Description




request
Ask clients for a certificate, but allow even if there isn't one; do not verify it


require
Require clients to present a certificate, but do not verify it


verify_if_given
Ask clients for a certificate; allow even if there isn't one, but verify it if there is


require_and_verify
Require clients to present a valid certificate that is verified



Default: require_and_verify if trust_pool module is provided; otherwise, require.


trust_pool  configures the source of certificate authorities (CA) providing certificates against which to validate client certificates.
The certificate authority used providing the pool of trusted certificates and the configuration within the segment depends on the configured source of trust pool module. The standard modules available in Caddy are listed below. The full list of modules, including 3rd-party, is listed in the trust_pool JSON documentation.
Multiple trusted_* directives may be used to specify multiple CA or leaf certificates. Client certificates which are not listed as one of the leaf certificates or signed by any of the specified CAs will be rejected according to the mode.


verifier  enables the use of a custom client certificate verifier module. These can perform custom client authentication checks, such as ensuring the certificate is not revoked.




issuer  configures a custom certificate issuer, or a source from which to obtain certificates.
Which issuer is used and the options that follow in this segment depend on the issuer modules that are available. Some of the other subdirectives such as ca and dns are actually shortcuts for configuring the acme issuer (and this subdirective was added later), so specifying this directive and some of the others is confusing and thus prohibited.
This subdirective can be specified multiple times to configure multiple, redundant issuers; if one fails to issue a cert, the next one will be tried.


get_certificate  enables getting certificates from a manager module at handshake-time.


insecure_secrets_log  enables logging of TLS secrets to a file. This is also known as SSLKEYLOGFILE. Uses NSS key log format, which can then be parsed by Wireshark or other tools. ⚠️ Security Warning: This is insecure as it allows other programs or tools to decrypt TLS connections, and therefore completely compromises security. However, this capability can be useful for debugging and troubleshooting.

</p>
<p></p>
""",
        examples = """<p></p>
Use a custom certificate and key. The certificate should have <a href="https://en.wikipedia.org/wiki/Subject_Alternative_Name">SANs</a> that match the site address:
<p></p>
<pre><code>example.com {
	tls cert.pem key.pem
}
</code></pre>
Use <a href="/docs/automatic-https#local-https">locally-trusted</a> certificates for all hosts on the current site block, rather than public certificates via ACME / Let's Encrypt (useful in dev environments):
<p></p>
<pre><code>example.com {
	tls internal
}
</code></pre>
Use locally-trusted certificates, but managed <a href="/docs/automatic-https#on-demand-tls">On-Demand</a> instead of in the background. This allows you to point any domain at your Caddy instance and have it automatically provision a certificate for you. This SHOULD NOT be used if your Caddy instance is publicly accessible, since an attacker could use it to exhaust your server's resources:
<p></p>
<pre><code>https:// {
	tls internal {
		on_demand
	}
}
</code></pre>
Use custom options for the internal CA (cannot use the <code>tls internal</code> shortcut):
<p></p>
<pre><code>example.com {
	tls {
		issuer internal {
			ca foo
		}
	}
}
</code></pre>
Specify an email address for your ACME account (but if only one email is used for all sites, we recommend the <code>email</code> <a href="/docs/caddyfile/options">global option</a> instead):
<p></p>
<pre><code>example.com {
	tls your@email.com
}
</code></pre>
Enable the DNS challenge for a domain managed on Cloudflare with account credentials in an environment variable. This unlocks wildcard certificate support, which requires DNS validation:
<p></p>
<pre><code>*.example.com {
	tls {
		dns cloudflare {env.CLOUDFLARE_API_TOKEN}
	}
}
</code></pre>
Get the certificate chain via HTTP, instead of having Caddy manage it. Note that <a href="#certificate-managers"><code>get_certificate</code></a> implies <a href="#on_demand"><code>on_demand</code></a> is enabled, fetching certificates using a module instead of triggering ACME issuance:
<p></p>
<pre><code>https:// {
	tls {
		get_certificate http http://localhost:9007/certs
	}
}
</code></pre>
Enable TLS Client Authentication and require clients to present a valid certificate that is verified against all the provided CA's via <code>trusted_ca_cert_file</code>
<p></p>
<pre><code>example.com {
	tls {
		client_auth {
			mode                 require_and_verify
			trusted_ca_cert_file ../caddy.ca.cer
			trusted_ca_cert_file ../root.ca.cer
		}
	}
}
</code></pre>
""",
    ),
    Directive(
        name = "tracing",
        description = """<p></p>
Enables integration with OpenTelemetry tracing facilities, using <a href="https://github.com/open-telemetry/opentelemetry-go"><code>opentelemetry-go</code> <img src="/old/resources/images/external-link.svg" class="external-link"></a>.
<p></p>
When enabled, it will propagate an existing trace context or initialize a new one.
<p></p>
It uses <a href="https://github.com/grpc/">gRPC</a> as an exporter protocol and  W3C <a href="https://www.w3.org/TR/trace-context/">tracecontext</a> and <a href="https://www.w3.org/TR/baggage/">baggage</a> as propagators.
<p></p>
The trace ID is added to <a href="/docs/caddyfile/directives/log">access logs</a> as the standard <code>traceID</code> field. Additionally, the <code>{http.vars.trace_id}</code> placeholder is made available, for example to add the ID to a (<code>request_header</code>)[request_header] to pass it to your app.
<p></p>
""",
        syntax = """<p></p>
<pre><code>tracing {
	[span &lt;span_name&gt;]
}
</code></pre>
<p>

&lt;span_name&gt; is a span name. Please see span naming guidelines.
Placeholders may be used in span names; keep in mind that tracing happens as early as possible, so only request placeholders may be used, and not response placeholders.

</p>
<p></p>
""",
        examples = """<p></p>
Here is a <strong>Caddyfile</strong> example:
<p></p>
<pre><code>example.com {
	handle /api* {
		tracing {
			span api
		}
		request_header X-Trace-Id {http.vars.trace_id}
		reverse_proxy localhost:8081
	}

	handle {
		tracing {
			span app
		}
		reverse_proxy localhost:8080
	}
}
</code></pre>
""",
    ),
    Directive(
        name = "try_files",
        description = """<p></p>
Rewrites the request URI path to the first of the listed files which exists in the site root. If no files match, no rewrite is performed.
<p></p>
""",
        syntax = """<p></p>
<pre><code>try_files &lt;files...&gt; {
	policy first_exist|smallest_size|largest_size|most_recently_modified
}
</code></pre>
<p>

&lt;files...&gt; is the list of files to try. The URI path will be rewritten to the first one that exists.
To match directories, append a trailing forward slash / to the path. All file paths are relative to the site root, and glob patterns will be expanded.
Each argument may also contain a query string, in which case the query string will also be changed if it matches that particular file.
If the try_policy is first_exist (the default), then the last item in the list may be a number prefixed by = (e.g. =404), which as a fallback, will emit an error with that code; the error can be caught and handled with handle_errors.


policy is the policy for choosing the file among the list of files.
Default: first_exist

</p>
<p></p>
""",
        examples = """<p></p>
If the request does not match any static files, rewrite to your PHP index/router entrypoint:
<p></p>
<pre><code>try_files {path} /index.php
</code></pre>
Same, but adding the original path to the query string (required by some legacy PHP apps):
<p></p>
<pre><code>try_files {path} /index.php?{query}&amp;p={path}
</code></pre>
Same, but also match directories:
<p></p>
<pre><code>try_files {path} {path}/ /index.php?{query}&amp;p={path}
</code></pre>
Attempt to rewrite to a file or directory if it exists, otherwise emit a 404 error (which can be caught and handled with <a href="handle_errors"><code>handle_errors</code></a>):
<p></p>
<pre><code>try_files {path} {path}/ =404
</code></pre>
Choose the most recently deployed version of a static file (e.g. serve <code>index.be331df.html</code> when <code>index.html</code> is requested):
<p></p>
<pre><code>try_files {file.base}.*.{file.ext} {
	policy most_recently_modified
}
</code></pre>
""",
    ),
    Directive(
        name = "uri",
        description = """<p></p>
Manipulates a request's URI. It can strip path prefix/suffix or replace substrings on the whole URI.
<p></p>
This directive is distinct from <a href="rewrite"><code>rewrite</code></a> in that <code>uri</code> <em>differentiably</em> changes the URI, rather than resetting it to something completely different as <code>rewrite</code> does. While <code>rewrite</code> is treated specially as an internal redirect, <code>uri</code> is just another middleware.
<p></p>
""",
        syntax = """<p></p>
Multiple different operations are supported:
<p></p>
<pre><code>uri [&lt;matcher&gt;] strip_prefix &lt;target&gt;
uri [&lt;matcher&gt;] strip_suffix &lt;target&gt;
uri [&lt;matcher&gt;] replace      &lt;target&gt; &lt;replacement&gt; [&lt;limit&gt;]
uri [&lt;matcher&gt;] path_regexp  &lt;target&gt; &lt;replacement&gt;
uri [&lt;matcher&gt;] query        [-|+]&lt;param&gt; [&lt;value&gt;]
uri [&lt;matcher&gt;] query {
	&lt;param&gt; [&lt;value&gt;] [&lt;replacement&gt;]
	...
}
</code></pre>
The first (non-matcher) argument specifies the operation:
<p></p>
<p>

strip_prefix strips the prefix from the path.


strip_suffix strips the suffix from the path.


replace performs a substring replacement across the whole URI.


&lt;target&gt; is the prefix, suffix, or search string/regular expression. If a prefix, the leading forward slash may be omitted, since paths always start with a forward slash.


&lt;replacement&gt; is the replacement string. Supports using capture groups with ${'$'}name or ${'$'}{name} syntax, or with a number for the index, such as $1. See the Go documentation for details. If the replacement value is "", then the matching text is removed from the value.


&lt;limit&gt; is an optional limit to the maximum number of replacements.




path_regexp performs a regular expression replacement on the path portion of the URI.


&lt;target&gt; is the prefix, suffix, or search string/regular expression. If a prefix, the leading forward slash may be omitted, since paths always start with a forward slash.


&lt;replacement&gt; is the replacement string. Supports using capture groups with ${'$'}name or ${'$'}{name} syntax, or with a number for the index, such as $1. See the Go documentation for details. If the replacement value is "", then the matching text is removed from the value.




query performs manipulations on the URI query, with the mode depending on the prefix to the parameter name or the count of arguments. A block can be used to specify multiple operations at once, grouped and performed in this order: rename 🡒 set 🡒 append 🡒 replace 🡒 delete.


With no prefix, the parameter is set with the given value in the query.
For example, uri query foo bar will set the value of the foo param to bar.


Prefix with - to remove the parameter from the query.
For example, uri query -foo will delete the foo parameter from the query.


Prefix with + to append a parameter to the query, with the given value. This will not overwrite an existing parameter with the same name (omit the + to overwrite).
For example, uri query +foo bar will append foo=bar to the query.


A param with &gt; as an infix will rename the parameter to the value after the &gt;.
For example, uri query foo&gt;bar will rename the foo parameter to bar.


With three arguments, query value regular expression replacement is performed, where the first arg is the query param name, the second is the search value, and the third is the replacement. The first arg (param name) may be * to perform the replacement on all query params.
Supports using capture groups with ${'$'}name or ${'$'}{name} syntax, or with a number for the index, such as $1. See the Go documentation for details. If the replacement value is "", then the matching text is removed from the value.
For example, uri query foo ^(ba)r $1z would replace the value of the foo param, where the value began with bar resulting in the value becoming baz.



</p>
<p></p>
URI mutations occur on the normalized or unescaped form of the URI. However, escape sequences can be used in the prefix or suffix patterns to match only those literal escapes at those positions in the request path. For example, <code>uri strip_prefix /a/b</code> will rewrite both <code>/a/b/c</code> and <code>/a%2Fb/c</code> to <code>/c</code>; and <code>uri strip_prefix /a%2Fb</code> will rewrite <code>/a%2Fb/c</code> to <code>/c</code>, but won't match <code>/a/b/c</code>.
<p></p>
The URI path is cleaned of directory traversal dots before modifications. Additionally, multiple slashes (such as <code>//</code>) are merged unless the <code>&lt;target&gt;</code> contains multiple slashes too.
<p></p>
""",
        examples = """<p></p>
Strip <code>/api</code> from the beginning of all request paths:
<p></p>
<pre><code>uri strip_prefix /api
</code></pre>
Strip <code>.php</code> from the end of all request paths:
<p></p>
<pre><code>uri strip_suffix .php
</code></pre>
Replace "/docs/" with "/v1/docs/" in any request URI:
<p></p>
<pre><code>uri replace /docs/ /v1/docs/
</code></pre>
Collapse all repeated slashes in the request path (but not the request query) to a single slash:
<p></p>
<pre><code>uri path_regexp /{2,} /
</code></pre>
Set the value of the <code>foo</code> query parameter to <code>bar</code>:
<p></p>
<pre><code>uri query foo bar
</code></pre>
Remove the <code>foo</code> parameter from the query:
<p></p>
<pre><code>uri query -foo
</code></pre>
Rename the <code>foo</code> query parameter to <code>bar</code>:
<p></p>
<pre><code>uri query foo&gt;bar
</code></pre>
Append the <code>bar</code> parameter to the query:
<p></p>
<pre><code>uri query +foo bar
</code></pre>
Replace the value of the <code>foo</code> query parameter where the value begins with <code>bar</code> with <code>baz</code>:
<p></p>
<pre><code>uri query foo ^(ba)r $1z
</code></pre>
Perform multiple query operations at once:
<p></p>
<pre><code>uri query {
	+foo bar
	-baz
	qux test
	renamethis&gt;renamed
}
</code></pre>
""",
    ),
    Directive(
        name = "vars",
        description = """<p></p>
Sets one or more variables to a particular value, to be used later in the request handling chain.
<p></p>
The primary way to access variables is with placeholders, which have the form <code>{vars.variable_name}</code>, or with the <a href="/docs/caddyfile/matchers#vars"><code>vars</code></a> and <a href="/docs/caddyfile/matchers#vars_regexp"><code>vars_regexp</code></a> request matchers.
<p></p>
You may use variables with the <a href="templates"><code>templates</code></a> directive using the <code>placeholder</code> function, for example: <code>{{placeholder "http.vars.variable_name"}}</code>
<p></p>
As a special case, it's possible to override the variable named <code>http.auth.user.id</code>, which is stored in the replacer, to update the <code>user_id</code> field in <a href="log">access logs</a>.
<p></p>
""",
        syntax = """<p></p>
<pre><code>vars [&lt;matcher&gt;] [&lt;name&gt; &lt;value&gt;] {
    &lt;name&gt; &lt;value&gt;
    ...
}
</code></pre>
<p>

&lt;name&gt; is the variable name to set.


&lt;value&gt; is the value of the variable.
The value will be type converted if possible; true and false will be converted to boolean types, and numeric values will be converted to integer or float accordingly. To avoid this conversion and keep them as strings, you may wrap them with quotes.

</p>
<p></p>
""",
        examples = """<p></p>
To set a single variable, the value being conditional based on the request path, then responding with the value:
<p></p>
<pre><code>example.com {
	vars /foo* isFoo "yep"
	vars isFoo "nope"

	respond {vars.isFoo}
}
</code></pre>
To set multiple variables, each converted to the appropriate scalar type:
<p></p>
<pre><code>vars {
	# boolean
	abc true

	# integer
	def 1

	# float
	ghi 2.3

	# string
	jkl "example"
}
</code></pre>
""",
    ),
)

