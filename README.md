File Upload Example
===================
Not really an example as much as me testing out different means of uploading files in Spring Boot apps.  The goal here was
to explore the different ways of configuring a Spring Boot app's handling of a multipart POST, to see what the most efficient
way was to handle uploading huge files.  Micro-services often have limited disk space, which makes them not ideal for a service
that allows multiple users to concurrently upload large-ish files if the multipart implementation creates temporary files on the
server for every file uploaded.  Unfortunately, this is standard behavior.

The long and short of playing around is:
* Both with commons-fileupload and without, uploaded files are stored either as a byte array in memory, or as a temporary file.  No way around this.
* It _might_ be possible to use commons-fileupload's request parsing API to read an uploaded file as a stream, and not buffered somehow.  See:  http://stackoverflow.com/questions/26172346/how-to-lazily-stream-using-httpservletrequestgetpartname.  I have not tested this yet.
* Can something be done with websockets?

Tried with and without commons-fileupload.  It appears that the Servlet 3.0 Parts API supersedes commons-fileupload, and
indeed, with Spring Boot the default parts implementation uses a version of commons-fileupload copied into Tomcat (or,
if using Jetty, Jetty has its own `MultiPartInputStreamParser.MultiPart` class for a Parts implementation that behaves
the same as that of commons-fileupload).

TODO:
* UI means of toggling between the different endpoints that parse uploads differently
* The UI in general is atrocious.  At least tidy it up to be "good" Angular design, if not convert it to React.
* Why is commons-fileupload not finding file parts?  Has Spring already parsed the request somehow and we need to stop it?  Raw Servlet implementation does not have this problem...
