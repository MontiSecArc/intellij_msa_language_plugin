match (c:Component)
with (:Component)-->(:Port)-->(:Port)-->(c) as path, c
  where size(path) > 1 and not c.name starts with "Access"
with extract(x in path | head(nodes(x))) as StartComponents, c, path
unwind StartComponents as sc
with count(distinct sc) as DistinctStartComponents, c, path
  where DistinctStartComponents > 1
return c;