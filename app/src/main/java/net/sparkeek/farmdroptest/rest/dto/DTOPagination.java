package net.sparkeek.farmdroptest.rest.dto;

import javax.annotation.Generated;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@Generated("com.robohorse.robopojogenerator")
@JsonObject
public class DTOPagination {

	@JsonField(name ="next")
	private int next;

	@JsonField(name ="per_page")
	private int perPage;

	@JsonField(name ="current")
	private int current;

	@JsonField(name ="pages")
	private int pages;

	@JsonField(name ="previous")
	private Object previous;

	@JsonField(name ="count")
	private int count;
}