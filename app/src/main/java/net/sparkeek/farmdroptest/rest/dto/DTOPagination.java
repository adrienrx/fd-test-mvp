package net.sparkeek.farmdroptest.rest.dto;

import javax.annotation.Generated;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@Generated("com.robohorse.robopojogenerator")
@JsonObject
public class DTOPagination {

	@JsonField(name ="next")
	public int next;

	@JsonField(name ="per_page")
	public int perPage;

	@JsonField(name ="current")
	public int current;

	@JsonField(name ="pages")
	public int pages;

	@JsonField(name ="previous")
	public Object previous;

	@JsonField(name ="count")
	public int count;
}