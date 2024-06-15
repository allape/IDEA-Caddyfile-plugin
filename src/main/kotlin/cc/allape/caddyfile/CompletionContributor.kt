package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

// Run this script in browser console of `https://caddyserver.com/docs/caddyfile/directives` to get the full list of directives
/*
/**
 * @param url {string}
 * @return {Promise<string[]>}
 */
async function getDirectives(url) {
    const content = await (await fetch(url)).text();
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, 'text/html');
    return Array.from(new Set(Array.from(doc.querySelectorAll('.chroma')).map(i => i.innerText.split('\n')).reduce((p, c) => [...p, ...c], []).map(i => i.trim().split(' ')[0]).filter(i => !!i && /^\w+$/.test(i))));
}
async function main() {
    const primaryDirectives = Array.from(document.querySelectorAll('#directive-table > table > tbody > tr > td:nth-child(1)')).map(i => i.innerText.trim());
    const all = await Promise.all(primaryDirectives.map(dir => getDirectives(`https://caddyserver.com/docs/caddyfile/directives/${dir}`)));
    console.log(Array.from(new Set([...primaryDirectives, ...all.reduce((p, c) => [...p, ...c])])).join('\n'));
}
main();
*/

val DIRECTIVES = """
abort
acme_server
basic_auth
bind
encode
error
file_server
forward_auth
fs
handle
handle_errors
handle_path
header
import
invoke
log
log_append
log_skip
map
method
metrics
php_fastcgi
push
redir
request_body
request_header
respond
reverse_proxy
rewrite
root
route
templates
tls
tracing
try_files
uri
vars
ca
lifetime
resolvers
challenges
allow_wildcard_names
allow
domains
ip_ranges
deny
pki
name
issuer
acme_ca
acme_ca_root
Bob
gzip
zstd
minimum_length
match
status
message
hide
index
browse
reveal_symlinks
precompressed
disable_canonical_uris
pass_thru
copy_headers
header_up
handle_response
filesystem
api_key
replace_status
transport
lb_policy
health_uri
health_interval
hostnames
output
format
level
roll_disabled
roll_size
roll_uncompressed
roll_local_time
roll_keep
roll_keep_for
dial_timeout
soft_start
message_key
level_key
time_key
name_key
caller_key
stacktrace_key
line_ending
time_format
time_local
duration_format
level_format
fields
wrap
ipv4
ipv6
delete
replace
hash
server_id
include
default
disable_openmetrics
split
env
resolve_root_symlink
capture_stderr
read_timeout
write_timeout
file
not
split_path
headers
max_size
body
close
HTML
to
dynamic
lb_retries
lb_try_duration
lb_try_interval
lb_retry_match
health_port
health_timeout
health_status
health_body
health_headers
fail_duration
max_fails
unhealthy_status
unhealthy_latency
unhealthy_request_count
flush_interval
request_buffers
response_buffers
stream_timeout
stream_close_delay
trusted_proxies
header_down
copy_response
copy_response_headers
exclude
service
proto
refresh
dial_fallback_delay
port
versions
fallback
echo
read_buffer
write_buffer
max_response_header
proxy_protocol
response_header_timeout
expect_continue_timeout
tls_client_auth
tls_insecure_skip_verify
tls_curves
tls_timeout
tls_trust_pool
tls_server_name
tls_renegotiation
tls_except_ports
keepalive
keepalive_interval
keepalive_idle_conns
keepalive_idle_conns_per_host
compression
max_conns_per_host
tls_trusted_ca_certs
mime
between
protocols
ciphers
curves
alpn
load
ca_root
key_type
dns
propagation_timeout
propagation_delay
dns_ttl
dns_challenge_override_domain
eab
on_demand
reuse_private_keys
client_auth
mode
trust_pool
trusted_leaf_cert
trusted_leaf_cert_file
verifier
get_certificate
insecure_secrets_log
trust_der
pem_file
authority
storage
keys
endpoints
insecure_skip_verify
handshake_timeout
server_name
renegotiation
dir
test_dir
email
timeout
disable_http_challenge
disable_tlsalpn_challenge
alt_http_port
alt_tlsalpn_port
trusted_roots
preferred_chains
root_common_name
any_common_name
sign_with_root
trusted_ca_cert_file
export
span
policy
abc
def
ghi
jkl
""".split("\n").filter { it.isNotBlank() }

internal class CaddyfileCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(CaddyfileTypes.DIRECTIVE),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    resultSet: CompletionResultSet
                ) {
                    for (directive in DIRECTIVES) {
                        resultSet.addElement(LookupElementBuilder.create(directive))
                    }
                }
            }
        )
    }
}