package cc.allape.caddyfile.documentation

//const val HttpHeaderDocURLPrefix = "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/"

/**
 * See https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers
 * @see ./httpheaders-documentation-generator.js
 */
val HTTP_HEADERS = mapOf(
    "WWW-Authenticate" to """<p>Defines the authentication method that should be used to access a resource.</p>""",
    "Authorization" to """<p>Contains the credentials to authenticate a user-agent with a server.</p>""",
    "Proxy-Authenticate" to """<p>Defines the authentication method that should be used to access a resource behind a proxy server.</p>""",
    "Proxy-Authorization" to """<p>Contains the credentials to authenticate a user agent with a proxy server.</p>""",
    "Age" to """<p>The time, in seconds, that the object has been in a proxy cache.</p>""",
    "Cache-Control" to """<p>Directives for caching mechanisms in both requests and responses.</p>""",
    "Clear-Site-Data" to """<p>Clears browsing data (e.g. cookies, storage, cache) associated with the requesting website.</p>""",
    "Expires" to """<p>The date/time after which the response is considered stale.</p>""",
    "No-Vary-Search" to """<p>Specifies a set of rules that define how a URL's query parameters will affect cache matching. These rules dictate whether the same URL with different URL parameters should be saved as separate browser cache entries.</p>""",
    "Last-Modified" to """<p>The last modification date of the resource, used to compare several versions of the same resource. It is less accurate than <a href="/en-US/docs/Web/HTTP/Headers/ETag"><code>ETag</code></a>, but easier to calculate in some environments. Conditional requests using <a href="/en-US/docs/Web/HTTP/Headers/If-Modified-Since"><code>If-Modified-Since</code></a> and <a href="/en-US/docs/Web/HTTP/Headers/If-Unmodified-Since"><code>If-Unmodified-Since</code></a> use this value to change the behavior of the request.</p>""",
    "ETag" to """<p>A unique string identifying the version of the resource. Conditional requests using <a href="/en-US/docs/Web/HTTP/Headers/If-Match"><code>If-Match</code></a> and <a href="/en-US/docs/Web/HTTP/Headers/If-None-Match"><code>If-None-Match</code></a> use this value to change the behavior of the request.</p>""",
    "If-Match" to """<p>Makes the request conditional, and applies the method only if the stored resource matches one of the given ETags.</p>""",
    "If-None-Match" to """<p>Makes the request conditional, and applies the method only if the stored resource <em>doesn't</em> match any of the given ETags. This is used to update caches (for safe requests), or to prevent uploading a new resource when one already exists.</p>""",
    "If-Modified-Since" to """<p>Makes the request conditional, and expects the resource to be transmitted only if it has been modified after the given date. This is used to transmit data only when the cache is out of date.</p>""",
    "If-Unmodified-Since" to """<p>Makes the request conditional, and expects the resource to be transmitted only if it has not been modified after the given date. This ensures the coherence of a new fragment of a specific range with previous ones, or to implement an optimistic concurrency control system when modifying existing documents.</p>""",
    "Vary" to """<p>Determines how to match request headers to decide whether a cached response can be used rather than requesting a fresh one from the origin server.</p>""",
    "Connection" to """<p>Controls whether the network connection stays open after the current transaction finishes.</p>""",
    "Keep-Alive" to """<p>Controls how long a persistent connection should stay open.</p>""",
    "Accept" to """<p>Informs the server about the <a href="/en-US/docs/Glossary/MIME_type">types</a> of data that can be sent back.</p>""",
    "Accept-Encoding" to """<p>The encoding algorithm, usually a <a href="/en-US/docs/Web/HTTP/Compression">compression algorithm</a>, that can be used on the resource sent back.</p>""",
    "Accept-Language" to """<p>Informs the server about the human language the server is expected to send back. This is a hint and is not necessarily under the full control of the user: the server should always pay attention not to override an explicit user choice (like selecting a language from a dropdown).</p>""",
    "Accept-Patch" to """<p>A <em>request content negotiation</em> response header that advertises which <a href="/en-US/docs/Web/HTTP/MIME_types">media type</a> the server is able to understand in a <a href="/en-US/docs/Web/HTTP/Methods/PATCH"><code>PATCH</code></a> request.</p>""",
    "Accept-Post" to """<p>A <em>request content negotiation</em> response header that advertises which <a href="/en-US/docs/Web/HTTP/MIME_types">media type</a> the server is able to understand in a <a href="/en-US/docs/Web/HTTP/Methods/POST"><code>POST</code></a> request.</p>""",
    "Expect" to """<p>Indicates expectations that need to be fulfilled by the server to properly handle the request.</p>""",
    "Max-Forwards" to """<p>When using <a href="/en-US/docs/Web/HTTP/Methods/TRACE"><code>TRACE</code></a>, indicates the maximum number of hops the request can do before being reflected to the sender.</p>""",
    "Cookie" to """<p>Contains stored <a href="/en-US/docs/Web/HTTP/Cookies">HTTP cookies</a> previously sent by the server with the <a href="/en-US/docs/Web/HTTP/Headers/Set-Cookie"><code>Set-Cookie</code></a> header.</p>""",
    "Set-Cookie" to """<p>Send cookies from the server to the user-agent.</p>""",
    "Access-Control-Allow-Credentials" to """<p>Indicates whether the response to the request can be exposed when the credentials flag is true.</p>""",
    "Access-Control-Allow-Headers" to """<p>Used in response to a <a href="/en-US/docs/Glossary/Preflight_request">preflight request</a> to indicate which HTTP headers can be used when making the actual request.</p>""",
    "Access-Control-Allow-Methods" to """<p>Specifies the methods allowed when accessing the resource in response to a preflight request.</p>""",
    "Access-Control-Allow-Origin" to """<p>Indicates whether the response can be shared.</p>""",
    "Access-Control-Expose-Headers" to """<p>Indicates which headers can be exposed as part of the response by listing their names.</p>""",
    "Access-Control-Max-Age" to """<p>Indicates how long the results of a preflight request can be cached.</p>""",
    "Access-Control-Request-Headers" to """<p>Used when issuing a preflight request to let the server know which HTTP headers will be used when the actual request is made.</p>""",
    "Access-Control-Request-Method" to """<p>Used when issuing a preflight request to let the server know which <a href="/en-US/docs/Web/HTTP/Methods">HTTP method</a> will be used when the actual request is made.</p>""",
    "Origin" to """<p>Indicates where a fetch originates from.</p>""",
    "Timing-Allow-Origin" to """<p>Specifies origins that are allowed to see values of attributes retrieved via features of the <a href="/en-US/docs/Web/API/Performance_API/Resource_timing">Resource Timing API</a>, which would otherwise be reported as zero due to cross-origin restrictions.</p>""",
    "Content-Disposition" to """<p>Indicates if the resource transmitted should be displayed inline (default behavior without the header), or if it should be handled like a download and the browser should present a "Save As" dialog.</p>""",
    "Content-Digest" to """<p>Provides a <a href="/en-US/docs/Glossary/Digest">digest</a> of the stream of octets framed in an HTTP message (the message content) dependent on <a href="/en-US/docs/Web/HTTP/Headers/Content-Encoding"><code>Content-Encoding</code></a> and <a href="/en-US/docs/Web/HTTP/Headers/Content-Range"><code>Content-Range</code></a>.</p>""",
    "Repr-Digest" to """<p>
      Provides a <a href="/en-US/docs/Glossary/Digest">digest</a> of the selected representation of the target resource before transmission.
      Unlike the <a href="/en-US/docs/Web/HTTP/Headers/Content-Digest"><code>Content-Digest</code></a>, the digest does not consider <a href="/en-US/docs/Web/HTTP/Headers/Content-Encoding"><code>Content-Encoding</code></a> or <a href="/en-US/docs/Web/HTTP/Headers/Content-Range"><code>Content-Range</code></a>.
    </p>""",
    "Want-Content-Digest" to """<p>
      States the wish for a <a href="/en-US/docs/Web/HTTP/Headers/Content-Digest"><code>Content-Digest</code></a> header.
      It is the <code>Content-</code> analogue of <a href="/en-US/docs/Web/HTTP/Headers/Want-Repr-Digest"><code>Want-Repr-Digest</code></a>.
    </p>""",
    "Want-Repr-Digest" to """<p>
      States the wish for a <a href="/en-US/docs/Web/HTTP/Headers/Repr-Digest"><code>Repr-Digest</code></a> header.
      It is the <code>Repr-</code> analogue of <a href="/en-US/docs/Web/HTTP/Headers/Want-Content-Digest"><code>Want-Content-Digest</code></a>.
    </p>""",
    "Content-Length" to """<p>The size of the resource, in decimal number of bytes.</p>""",
    "Content-Type" to """<p>Indicates the media type of the resource.</p>""",
    "Content-Encoding" to """<p>Used to specify the compression algorithm.</p>""",
    "Content-Language" to """<p>Describes the human language(s) intended for the audience, so that it allows a user to differentiate according to the users' own preferred language.</p>""",
    "Content-Location" to """<p>Indicates an alternate location for the returned data.</p>""",
    "Forwarded" to """<p>Contains information from the client-facing side of proxy servers that is altered or lost when a proxy is involved in the path of the request.</p>""",
    "Via" to """<p>Added by proxies, both forward and reverse proxies, and can appear in the request headers and the response headers.</p>""",
    "Accept-Ranges" to """<p>Indicates if the server supports range requests, and if so in which unit the range can be expressed.</p>""",
    "Range" to """<p>Indicates the part of a document that the server should return.</p>""",
    "If-Range" to """<p>Creates a conditional range request that is only fulfilled if the given etag or date matches the remote resource. Used to prevent downloading two ranges from incompatible version of the resource.</p>""",
    "Content-Range" to """<p>Indicates where in a full body message a partial message belongs.</p>""",
    "Location" to """<p>Indicates the URL to redirect a page to.</p>""",
    "Refresh" to """<p>Directs the browser to reload the page or redirect to another. Takes the same value as the <code>meta</code> element with <a href="/en-US/docs/Web/HTML/Element/meta#http-equiv"><code>http-equiv="refresh"</code></a>.</p>""",
    "From" to """<p>Contains an Internet email address for a human user who controls the requesting user agent.</p>""",
    "Host" to """<p>Specifies the domain name of the server (for virtual hosting), and (optionally) the TCP port number on which the server is listening.</p>""",
    "Referer" to """<p>The address of the previous web page from which a link to the currently requested page was followed.</p>""",
    "Referrer-Policy" to """<p>Governs which referrer information sent in the <a href="/en-US/docs/Web/HTTP/Headers/Referer"><code>Referer</code></a> header should be included with requests made.</p>""",
    "User-Agent" to """<p>Contains a characteristic string that allows the network protocol peers to identify the application type, operating system, software vendor or software version of the requesting software user agent.</p>""",
    "Allow" to """<p>Lists the set of HTTP request methods supported by a resource.</p>""",
    "Server" to """<p>Contains information about the software used by the origin server to handle the request.</p>""",
    "Cross-Origin-Embedder-Policy" to """<p>Allows a server to declare an embedder policy for a given document.</p>""",
    "Cross-Origin-Opener-Policy" to """<p>Prevents other domains from opening/controlling a window.</p>""",
    "Cross-Origin-Resource-Policy" to """<p>Prevents other domains from reading the response of the resources to which this header is applied. See also <a href="/en-US/docs/Web/HTTP/Cross-Origin_Resource_Policy">CORP explainer article</a>.</p>""",
    "Content-Security-Policy" to """<p>Controls resources the user agent is allowed to load for a given page.</p>""",
    "Content-Security-Policy-Report-Only" to """<p>Allows web developers to experiment with policies by monitoring, but not enforcing, their effects. These violation reports consist of <a href="/en-US/docs/Glossary/JSON">JSON</a> documents sent via an HTTP <code>POST</code> request to the specified URI.</p>""",
    "Expect-CT" to """<p>Lets sites opt in to reporting and enforcement of <a href="/en-US/docs/Web/Security/Certificate_Transparency">Certificate Transparency</a> to detect use of misissued certificates for that site.</p>""",
    "Permissions-Policy" to """<p>Provides a mechanism to allow and deny the use of browser features in a website's own frame, and in <a href="/en-US/docs/Web/HTML/Element/iframe"><code>&lt;iframe&gt;</code></a>s that it embeds.</p>""",
    "Reporting-Endpoints" to """<p>Response header that allows website owners to specify one or more endpoints used to receive errors such as CSP violation reports, <a href="/en-US/docs/Web/HTTP/Headers/Cross-Origin-Opener-Policy"><code>Cross-Origin-Opener-Policy</code></a> reports, or other generic violations.</p>""",
    "Strict-Transport-Security" to """<p>Force communication using HTTPS instead of HTTP.</p>""",
    "Upgrade-Insecure-Requests" to """<p>Sends a signal to the server expressing the client's preference for an encrypted and authenticated response, and that it can successfully handle the <a href="/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/upgrade-insecure-requests"><code>upgrade-insecure-requests</code></a> directive.</p>""",
    "X-Content-Type-Options" to """<p>Disables MIME sniffing and forces browser to use the type given in <a href="/en-US/docs/Web/HTTP/Headers/Content-Type"><code>Content-Type</code></a>.</p>""",
    "X-Frame-Options" to """<p>Indicates whether a browser should be allowed to render a page in a <a href="/en-US/docs/Web/HTML/Element/frame"><code>&lt;frame&gt;</code></a>, <a href="/en-US/docs/Web/HTML/Element/iframe"><code>&lt;iframe&gt;</code></a>, <a href="/en-US/docs/Web/HTML/Element/embed"><code>&lt;embed&gt;</code></a> or <a href="/en-US/docs/Web/HTML/Element/object"><code>&lt;object&gt;</code></a>.</p>""",
    "X-Permitted-Cross-Domain-Policies" to """<p>Specifies if a cross-domain policy file (<code>crossdomain.xml</code>) is allowed. The file may define a policy to grant clients, such as Adobe's Flash Player (now obsolete), Adobe Acrobat, Microsoft Silverlight (now obsolete), or Apache Flex, permission to handle data across domains that would otherwise be restricted due to the <a href="/en-US/docs/Web/Security/Same-origin_policy">Same-Origin Policy</a>. See the <a href="https://www.adobe.com/devnet-docs/acrobatetk/tools/AppSec/CrossDomain_PolicyFile_Specification.pdf" class="external" target="_blank">Cross-domain Policy File Specification</a> for more information.</p>""",
    "X-Powered-By" to """<p>May be set by hosting environments or other frameworks and contains information about them while not providing any usefulness to the application or its visitors. Unset this header to avoid exposing potential vulnerabilities.</p>""",
    "X-XSS-Protection" to """<p>Enables cross-site scripting filtering.</p>""",
    "Sec-Fetch-Site" to """<p>Indicates the relationship between a request initiator's origin and its target's origin. It is a Structured Header whose value is a token with possible values <code>cross-site</code>, <code>same-origin</code>, <code>same-site</code>, and <code>none</code>.</p>""",
    "Sec-Fetch-Mode" to """<p>Indicates the request's mode to a server. It is a Structured Header whose value is a token with possible values <code>cors</code>, <code>navigate</code>, <code>no-cors</code>, <code>same-origin</code>, and <code>websocket</code>.</p>""",
    "Sec-Fetch-User" to """<p>Indicates whether or not a navigation request was triggered by user activation. It is a Structured Header whose value is a boolean so possible values are <code>?0</code> for false and <code>?1</code> for true.</p>""",
    "Sec-Fetch-Dest" to """<p>Indicates the request's destination. It is a Structured Header whose value is a token with possible values <code>audio</code>, <code>audioworklet</code>, <code>document</code>, <code>embed</code>, <code>empty</code>, <code>font</code>, <code>image</code>, <code>manifest</code>, <code>object</code>, <code>paintworklet</code>, <code>report</code>, <code>script</code>, <code>serviceworker</code>, <code>sharedworker</code>, <code>style</code>, <code>track</code>, <code>video</code>, <code>worker</code>, and <code>xslt</code>.</p>""",
    "Sec-Purpose" to """<p>Indicates the purpose of the request, when the purpose is something other than immediate use by the user-agent. The header currently has one possible value, <code>prefetch</code>, which indicates that the resource is being fetched preemptively for a possible future navigation.</p>""",
    "Service-Worker-Navigation-Preload" to """<p>A request header sent in preemptive request to <a href="/en-US/docs/Web/API/Window/fetch" title="fetch()"><code>fetch()</code></a> a resource during service worker boot. The value, which is set with <a href="/en-US/docs/Web/API/NavigationPreloadManager/setHeaderValue"><code>NavigationPreloadManager.setHeaderValue()</code></a>, can be used to inform a server that a different resource should be returned than in a normal <code>fetch()</code> operation.</p>""",
    "Reporting-Endpoints" to """<p>Response header used to specify server endpoints where the browser should send warning and error reports when using the <a href="/en-US/docs/Web/API/Reporting_API">Reporting API</a>.</p>""",
    "Report-To" to """<p>Response header used to specify server endpoints where the browser should send warning and error reports when using the <a href="/en-US/docs/Web/API/Reporting_API">Reporting API</a>.</p>""",
    "Transfer-Encoding" to """<p>Specifies the form of encoding used to safely transfer the resource to the user.</p>""",
    "TE" to """<p>Specifies the transfer encodings the user agent is willing to accept.</p>""",
    "Trailer" to """<p>Allows the sender to include additional fields at the end of chunked message.</p>""",
    "Sec-WebSocket-Accept" to """<p>Response header that indicates that the server is willing to upgrade to a WebSocket connection.</p>""",
    "Sec-WebSocket-Extensions" to """<p>
      In requests, this header indicates the WebSocket extensions supported by the client in preferred order.
      In responses, it indicates the extension selected by the server from the client's preferences.
    </p>""",
    "Sec-WebSocket-Key" to """<p>Request header containing a key that verifies that the client explicitly intends to open a <code>WebSocket</code>.</p>""",
    "Sec-WebSocket-Protocol" to """<p>
      In requests, this header indicates the sub-protocols supported by the client in preferred order.
      In responses, it indicates the the sub-protocol selected by the server from the client's preferences.
    </p>""",
    "Sec-WebSocket-Version" to """<p>
      In requests, this header indicates the version of the WebSocket protocol used by the client.
      In responses, it is sent only if the requested protocol version is not supported by the server, and lists the versions that the server supports.
    </p>""",
    "Alt-Svc" to """<p>Used to list alternate ways to reach this service.</p>""",
    "Alt-Used" to """<p>Used to identify the alternative service in use.</p>""",
    "Date" to """<p>Contains the date and time at which the message was originated.</p>""",
    "Link" to """<p>This entity-header field provides a means for serializing one or more links in HTTP headers. It is semantically equivalent to the HTML <a href="/en-US/docs/Web/HTML/Element/link"><code>&lt;link&gt;</code></a> element.</p>""",
    "Retry-After" to """<p>Indicates how long the user agent should wait before making a follow-up request.</p>""",
    "Server-Timing" to """<p>Communicates one or more metrics and descriptions for the given request-response cycle.</p>""",
    "Service-Worker-Allowed" to """<p>Used to remove the <a href="/en-US/docs/Web/API/Service_Worker_API/Using_Service_Workers#why_is_my_service_worker_failing_to_register">path restriction</a> by including this header <a href="https://w3c.github.io/ServiceWorker/#service-worker-script-response" class="external" target="_blank">in the response of the Service Worker script</a>.</p>""",
    "SourceMap" to """<p>Links generated code to a <a href="https://firefox-source-docs.mozilla.org/devtools-user/debugger/how_to/use_a_source_map/index.html" class="external" target="_blank">source map</a>.</p>""",
    "Upgrade" to """<p>This HTTP/1.1 (only) header can be used to upgrade an already established client/server connection to a different protocol (over the same transport protocol). For example, it can be used by a client to upgrade a connection from HTTP 1.1 to HTTP 2.0, or an HTTP or HTTPS connection into a WebSocket.</p>""",
    "Priority" to """<p>
      Provides a hint from about the priority of a particular resource request on a particular connection.
      The value can be sent in a request to indicate the client priority, or in a response if the server chooses to reprioritize the request.
    </p>""",
    "Attribution-Reporting-Eligible" to """<p>Used to indicate that the response corresponding to the current request is eligible to take part in attribution reporting, by registering either an attribution source or trigger.</p>""",
    "Attribution-Reporting-Register-Source" to """<p>Included as part of a response to a request that included an <code>Attribution-Reporting-Eligible</code> header, this is used to register an attribution source.</p>""",
    "Attribution-Reporting-Register-Trigger" to """<p>Included as part of a response to a request that included an <code>Attribution-Reporting-Eligible</code> header, this is used to register an attribution trigger.</p>""",
    "Accept-CH" to """<p>Servers can advertise support for Client Hints using the <code>Accept-CH</code> header field or an equivalent HTML <code>&lt;meta&gt;</code> element with <a href="/en-US/docs/Web/HTML/Element/meta#http-equiv"><code>http-equiv</code></a> attribute.</p>""",
    "Critical-CH" to """<p>Servers use <code>Critical-CH</code> along with <a href="/en-US/docs/Web/HTTP/Headers/Accept-CH"><code>Accept-CH</code></a> to specify that accepted client hints are also <a href="/en-US/docs/Web/HTTP/Client_hints#critical_client_hints">critical client hints</a>.</p>""",
    "Sec-CH-UA" to """<p>User agent's branding and version.</p>""",
    "Sec-CH-UA-Arch" to """<p>User agent's underlying platform architecture.</p>""",
    "Sec-CH-UA-Bitness" to """<p>User agent's underlying CPU architecture bitness (for example "64" bit).</p>""",
    "Sec-CH-UA-Form-Factor" to """<p>User agent's form-factors, describing how the user interacts with the user-agent.</p>""",
    "Sec-CH-UA-Full-Version" to """<p>User agent's full version string.</p>""",
    "Sec-CH-UA-Full-Version-List" to """<p>Full version for each brand in the user agent's brand list.</p>""",
    "Sec-CH-UA-Mobile" to """<p>User agent is running on a mobile device or, more generally, prefers a "mobile" user experience.</p>""",
    "Sec-CH-UA-Model" to """<p>User agent's device model.</p>""",
    "Sec-CH-UA-Platform" to """<p>User agent's underlying operation system/platform.</p>""",
    "Sec-CH-UA-Platform-Version" to """<p>User agent's underlying operation system version.</p>""",
    "Sec-CH-UA-WoW64" to """<p>Whether or not the user agent binary is running in 32-bit mode on 64-bit Windows.</p>""",
    "Sec-CH-Prefers-Color-Scheme" to """<p>User's preference of dark or light color scheme.</p>""",
    "Sec-CH-Prefers-Reduced-Motion" to """<p>User's preference to see fewer animations and content layout shifts.</p>""",
    "Sec-CH-Prefers-Reduced-Transparency" to """<p>Request header indicates the user agent's preference for reduced transparency.</p>""",
    "Content-DPR" to """<p>Response header used to confirm the image device to pixel ratio (DPR) in requests where the screen <a href="/en-US/docs/Web/HTTP/Headers/DPR"><code>DPR</code></a> client hint was used to select an image resource.</p>""",
    "Device-Memory" to """<p>Approximate amount of available client RAM memory. This is part of the <a href="/en-US/docs/Web/API/Device_Memory_API">Device Memory API</a>.</p>""",
    "DPR" to """<p>Request header that provides the client device pixel ratio (the number of physical device pixels for each <a href="/en-US/docs/Glossary/CSS_pixel">CSS pixel</a>).</p>""",
    "Viewport-Width" to """<p>Request header provides the client's layout viewport width in <a href="/en-US/docs/Glossary/CSS_pixel">CSS pixels</a>.</p>""",
    "Width" to """<p>Request header indicates the desired resource width in physical pixels (the intrinsic size of an image).</p>""",
    "Downlink" to """<p>Approximate bandwidth of the client's connection to the server, in Mbps. This is part of the <a href="/en-US/docs/Web/API/Network_Information_API">Network Information API</a>.</p>""",
    "ECT" to """<p>The <a href="/en-US/docs/Glossary/Effective_connection_type">effective connection type</a> ("network profile") that best matches the connection's latency and bandwidth. This is part of the <a href="/en-US/docs/Web/API/Network_Information_API">Network Information API</a>.</p>""",
    "RTT" to """<p>Application layer round trip time (RTT) in milliseconds, which includes the server processing time. This is part of the <a href="/en-US/docs/Web/API/Network_Information_API">Network Information API</a>.</p>""",
    "Save-Data" to """<p>A string <code>on</code> that indicates the user agent's preference for reduced data usage.</p>""",
    "DNT" to """<p>
      Request header that indicates the user's tracking preference (Do Not Track).
      Deprecated in favor of Global Privacy Control (GPC), which is communicated to servers using the <a href="/en-US/docs/Web/HTTP/Headers/Sec-GPC"><code>Sec-GPC</code></a> header, and accessible to clients via <a href="/en-US/docs/Web/API/Navigator/globalPrivacyControl"><code>navigator.globalPrivacyControl</code></a>.
    </p>""",
    "Tk" to """<p>Response header that indicates the tracking status that applied to the corresponding request. Used in conjunction with DNT.</p>""",
    "Sec-GPC" to """<p>Indicates whether the user consents to a website or service selling or sharing their personal information with third parties.</p>""",
    "Origin-Isolation" to """<p>Provides a mechanism to allow web applications to isolate their origins.</p>""",
    "NEL" to """<p>Defines a mechanism that enables developers to declare a network error reporting policy.</p>""",
    "Observe-Browsing-Topics" to """<p>Response header used to mark topics of interest inferred from a calling site's URL as observed in the response to a request generated by a <a href="/en-US/docs/Web/API/Topics_API/Using#what_api_features_enable_the_topics_api">feature that enables the Topics API</a>.</p>""",
    "Sec-Browsing-Topics" to """<p>Request header that sends the selected topics for the current user along with the associated request, which are used by an ad tech platform to choose a personalized ad to display.</p>""",
    "Accept-Signature" to """<p>A client can send the <a href="https://wicg.github.io/webpackage/draft-yasskin-http-origin-signed-responses.html#name-the-accept-signature-header" class="external" target="_blank"><code>Accept-Signature</code></a> header field to indicate intention to take advantage of any available signatures and to indicate what kinds of signatures it supports.</p>""",
    "Early-Data" to """<p>Indicates that the request has been conveyed in TLS early data.</p>""",
    "Origin-Agent-Cluster" to """<p>
      Response header used to indicate that the associated <a href="/en-US/docs/Web/API/Document"><code>Document</code></a> should be placed in an <em>origin-keyed <a href="https://tc39.es/ecma262/#sec-agent-clusters" class="external" target="_blank">agent cluster</a></em>.
      This isolation allows user agents to allocate implementation-specific resources for agent clusters, such as processes or threads, more efficiently.
    </p>""",
    "Set-Login" to """<p>
      Response header sent by a federated identity provider (IdP) to set its login status, meaning whether any users are logged into the IdP on the current browser or not.
      This is stored by the browser and used by the <a href="/en-US/docs/Web/API/FedCM_API">FedCM API</a>.
    </p>""",
    "Signature" to """<p>The <a href="https://wicg.github.io/webpackage/draft-yasskin-http-origin-signed-responses.html#name-the-signature-header" class="external" target="_blank"><code>Signature</code></a> header field conveys a list of signatures for an exchange, each one accompanied by information about how to determine the authority of and refresh that signature.</p>""",
    "Signed-Headers" to """<p>The <a href="https://wicg.github.io/webpackage/draft-yasskin-http-origin-signed-responses.html#name-the-signed-headers-header" class="external" target="_blank"><code>Signed-Headers</code></a> header field identifies an ordered list of response header fields to include in a signature.</p>""",
    "Speculation-Rules" to """<p>Provides a list of URLs pointing to text resources containing <a href="/en-US/docs/Web/API/Speculation_Rules_API">speculation rule</a> JSON definitions. When the response is an HTML document, these rules will be added to the document's speculation rule set.</p>""",
    "Supports-Loading-Mode" to """<p>Set by a navigation target to opt-in to using various higher-risk loading modes. For example, cross-origin, same-site <a href="/en-US/docs/Web/API/Speculation_Rules_API#using_prerendering">prerendering</a> requires a <code>Supports-Loading-Mode</code> value of <code>credentialed-prerender</code>.</p>""",
    "X-Forwarded-For" to """<p>Identifies the originating IP addresses of a client connecting to a web server through an HTTP proxy or a load balancer.</p>""",
    "X-Forwarded-Host" to """<p>Identifies the original host requested that a client used to connect to your proxy or load balancer.</p>""",
    "X-Forwarded-Proto" to """<p>Identifies the protocol (HTTP or HTTPS) that a client used to connect to your proxy or load balancer.</p>""",
    "X-DNS-Prefetch-Control" to """<p>Controls DNS prefetching, a feature by which browsers proactively perform domain name resolution on both links that the user may choose to follow as well as URLs for items referenced by the document, including images, CSS, JavaScript, and so forth.</p>""",
    "X-Robots-Tag" to """<p>The <a href="https://developers.google.com/search/docs/crawling-indexing/robots-meta-tag" class="external" target="_blank"><code>X-Robots-Tag</code></a> HTTP header is used to indicate how a web page is to be indexed within public search engine results. The header is effectively equivalent to <code>&lt;meta name="robots" content="…"&gt;</code>.</p>""",
    "Pragma" to """<p>Implementation-specific header that may have various effects anywhere along the request-response chain. Used for backwards compatibility with HTTP/1.0 caches where the <code>Cache-Control</code> header is not yet present.</p>""",
    "Warning" to """<p>General warning information about possible problems.</p>""",
)
