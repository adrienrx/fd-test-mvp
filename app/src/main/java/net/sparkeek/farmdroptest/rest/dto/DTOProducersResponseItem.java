package net.sparkeek.farmdroptest.rest.dto;

import java.util.List;
import javax.annotation.Generated;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@Generated("com.robohorse.robopojogenerator")
@JsonObject
public class DTOProducersResponseItem {

	@JsonField(name ="DTOPagination")
	public DTOPagination DTOPagination;

	@JsonField(name ="response")
	public List<DTOProducers> response;

	@JsonField(name ="count")
	public int count;
}