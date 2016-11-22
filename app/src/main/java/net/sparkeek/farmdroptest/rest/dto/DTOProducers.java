package net.sparkeek.farmdroptest.rest.dto;

import java.util.List;
import javax.annotation.Generated;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@Generated("com.robohorse.robopojogenerator")
@JsonObject
public class DTOProducers{

	@JsonField(name ="DTOPagination")
	private DTOPagination DTOPagination;

	@JsonField(name ="response")
	private List<DTOResponseItem> response;

	@JsonField(name ="count")
	private int count;
}