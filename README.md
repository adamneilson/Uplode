# Uplode

I found myself scouring the Interweb for an example of uploading images using Compojure/Ring but didn't really find a good example for my needs. So I thought I'd write one.

This uploads images to Amazon S3.

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

You'll also need an AWS account and the access credentials for an S3 bucket.

In `handler.clj` configure the access credentials and also the `bucket`. Also change the max file size which is set to 1MB.

## Running

To start a web server for the application, run:

    lein ring server

Then point your browser at `http://localhost:3000/` and upload images!

## License

Copyright © 2013 Adam Neilson
