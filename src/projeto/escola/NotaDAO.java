package projeto.escola;

import java.util.List;

public interface NotaDAO {
	void create(Nota n);
	List<Nota> pesquisarPorRA(String ra);
	void apagar(Nota n);
	void atualizar(String nomeAntigo, Nota n);
}
