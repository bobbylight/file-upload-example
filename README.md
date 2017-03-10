File Upload Example
===================
Not really an example as much as me testing out different means of uploading files in Spring Boot apps.  The goal here was
to explore the different ways of configuring a Spring Boot app's handling of a multipart POST, to see what the most efficient
way was to handle uploading huge files.  Micro-services often have limited disk space, which makes them not ideal for a service
that allows multiple users to concurrently upload large-ish files if the multipart implementation creates temporary files on the
server for every file uploaded.  Unfortunately, this is standard behavior.

The long and short of playing around is:
* With their standard APIs, both with commons-fileupload and without, uploaded files are stored either as a byte array in memory, or as a temporary file.
  There's no way around this.
* It's possible to use commons-fileupload's request parsing API to read an uploaded file as a stream, directly off of the request,
  without it being buffered into memory or a file.  See `UploadServlet` and `UploadController.getViaCommonsFileUploadStreamingApi()`.
* Websockets aren't explored, but might be a cool way to do things too.

You can configure things to use commons-fileupload, or the standard Spring multipart implementation.  It appears that the
Servlet 3.0 Parts API supersedes commons-fileupload, and indeed, with Spring Boot the default parts implementation uses
a version of commons-fileupload copied into Tomcat (or, if using Jetty, Jetty has its own
`MultiPartInputStreamParser.MultiPart` class for a Parts implementation that behaves the same as that of commons-fileupload).
However, I believe using the Parts API always buffers files into memory/disk, so the parts can be accessed in arbitrary
order.

TODO:
* UI means of toggling between the different endpoints that parse uploads differently
* The UI in general is atrocious.  At least tidy it up to be "good" Angular design, if not convert it to React.
* Why is commons-fileupload not finding file parts?  Has Spring already parsed the request somehow and we need to stop it?  Raw Servlet implementation does not have this problem...
