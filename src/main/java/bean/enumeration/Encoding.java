package bean.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum Encoding {
	
	UTF8("UTF-8"), ;

	@Getter
	@Setter
	private String valeur;
}
