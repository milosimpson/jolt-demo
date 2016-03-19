// The Json parser that supports comments is a Node thing.   Use Browserify to turn it (and more importantly its
//  dependencies) in to a single .js file for use in the browser.
//
// npm install comment-json
// npm install -g browserify
//
// browserify nodeJsonParserLib.js -o ../server/src/main/webapp/lib/browserifiedNodeJsonParser.js
//
nodeJsonParser = require('comment-json');
