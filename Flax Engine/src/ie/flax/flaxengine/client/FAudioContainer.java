package ie.flax.flaxengine.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;

public class FAudioContainer implements JsonSerializable {

	private String tagId;
	private String[] sources;
	
	public String getTagId() {
		return tagId;
	}

	@Deprecated
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	@Deprecated
	public String[] getSources() {
		return sources;
	}

	@Deprecated
	public void setSources(String[] sources) {
		this.sources = sources;
	}
	
	public FAudioContainer() {
		String htmlAsString;
		
		
		//generating html
		htmlAsString = "<audio id="+this.tagId+" controls>";
		
		for (int i = 0; i < sources.length; i++) {
			htmlAsString.concat("<source src="+sources[i]+">");
		}
		
		htmlAsString.concat("</audio>");
		
		HTML myHtml = new HTML(htmlAsString);
		
		RootPanel.get().add(myHtml);
	}
}
