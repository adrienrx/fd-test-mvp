package net.sparkeek.farmdroptest.rest.dto;

import java.util.List;
import javax.annotation.Generated;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@Generated("com.robohorse.robopojogenerator")
@JsonObject
public class DTOProducers {

	@JsonField(name ="short_description")
	public String shortDescription;

	@JsonField(name ="images")
	public List<DTOImagesItem> images;

	@JsonField(name ="via_wholesaler")
	public boolean viaWholesaler;

	@JsonField(name ="updated_at")
	public String updatedAt;

	@JsonField(name ="name")
	public String name;

	@JsonField(name ="created_at")
	public String createdAt;

	@JsonField(name ="description")
	public String description;

	@JsonField(name ="location")
	public String location;

	@JsonField(name ="id")
	public int id;

	@JsonField(name ="permalink")
	public String permalink;

	@JsonField(name ="wholesaler_name")
	public String wholesalerName;
}