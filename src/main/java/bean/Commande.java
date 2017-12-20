package bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Commande {

	@Getter
	@Setter
	private Position position;
	
	@Getter
	@Setter
	private List<String> operations;
}
