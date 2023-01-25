# 5 Algorithmic task

## Preliminary research: 2 hr
## Time to complete: 5 min

<br> 

Storing the documents in a Database, with a column for the document, and a column for the checksum, where the checksum is calculated when a document gets uploaded. Index the table based on the checksum ask det database if the incoming checksum already exists through a stored procedure.

That's how I would do it for this scope, but the best solution I could come up with for this case, would be to use a Distributed Hash Table (DHT) with a consistens hashing algorithm.

The DHT solution is a way to handle the problem of checking incoming documents for duplicates by using a distributed hash table with a consistent hashing algorithm for data distribution and a Bloom filter for duplicate detection. The DHT would be implemented in a way that when a new document is received, the checksum is computed and the distributed hash table is used to check if a document with that checksum already exists. The DHT would be efficient for large datasets and a high number of concurrent requests, it would be significantly faster, but it would be more complex and resource-intensive to implement and maintain than the indexed solution.

I used OpenAI's ChatGPT to estimate the lookup time difference. This response is based on the case you provided, and the descriptions you read above:

```
As for the lookup time difference for the two solutions, it will depend on the specific hardware and software setup, as well as the complexity of the hash function and Bloom filter algorithm used. However, based on the information provided, the DHT solution would be significantly faster than the indexed solution in this particular use case. A DHT would have a lookup time measured in milliseconds, whereas the index solution would likely have a lookup time measured in seconds or even minutes.
```
