package opwvhk.webdav;

import java.net.URI;
import java.time.Instant;

public class WebDavEntry {
	/// URI of the file/folder, relative to the WebDAV server root
	private URI uri;
	/// The date and time when the file was created
	private Instant creationDate;
	/// The date and time when the file was last modified
	private Instant lastModified;
	/// Indicates whether the file is read-only
	private boolean isReadOnly;
	/// Indicates whether the file is hidden
	private boolean isHidden;
	///  Indicates whether access to the file is accepted (if not, clients will get a "400 Bad Request" response)
	private boolean isAccepted;
	///  Indicates whether access to the file is permitted (if not, clients will get a "404 Not Found" response)
	private boolean isPermitted;
}
