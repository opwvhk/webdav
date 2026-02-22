package opwvhk.webdav;

import java.util.List;

public class WebDavFolder extends WebDavEntry {
	// The contents of this directory
	List<WebDavEntry> collection;

	// TODO: derive lastUpdated from the entries where useful
}
