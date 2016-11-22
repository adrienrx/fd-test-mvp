package net.sparkeek.farmdroptest.rest.dto;

import java.util.List;
import javax.annotation.Generated;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@Generated("com.robohorse.robopojogenerator")
@JsonObject
public class DTOResponseItem {

	@JsonField(name ="short_description")
	private String shortDescription;

	@JsonField(name ="images")
	private List<DTOImagesItem> images;

	@JsonField(name ="via_wholesaler")
	private boolean viaWholesaler;

	@JsonField(name ="updated_at")
	private String updatedAt;

	@JsonField(name ="name")
	private String name;

	@JsonField(name ="created_at")
	private String createdAt;

	@JsonField(name ="description")
	private String description;

	@JsonField(name ="location")
	private String location;

	@JsonField(name ="id")
	private int id;

	@JsonField(name ="permalink")
	private String permalink;

	@JsonField(name ="wholesaler_name")
	private String wholesalerName;
}