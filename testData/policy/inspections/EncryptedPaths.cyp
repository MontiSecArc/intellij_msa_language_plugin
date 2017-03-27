match (n:Port)-[r:UNENCRYPTED|ENCRYPTED|INGOING|OUTGOING*]->(m:Port)
with    filter(x IN r WHERE type(x) = "UNENCRYPTED") as unencrypted_links,
        size(filter(x IN r WHERE type(x) = "ENCRYPTED")) AS encrypted_link_size
where size(unencrypted_links) > 0 and encrypted_link_size > 0
return unencrypted_links;