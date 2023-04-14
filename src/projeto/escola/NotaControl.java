package projeto.escola;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NotaControl {
	private StringProperty ra = new SimpleStringProperty("");
	private DoubleProperty n1 = new SimpleDoubleProperty(0.0);
	private DoubleProperty n2 = new SimpleDoubleProperty(0.0);
	
	private NotaDAO notaDao = new NotaDAOImpl();
	
	private ObservableList<Nota> listaNota = FXCollections.observableArrayList();
	
	private boolean editando = false;
	private String raAntigo = null;
	
	public Nota getEntity() {
		Nota n = new Nota();
		n.setRa(ra.get());
		n.setN1(n1.get());
		n.setN2(n2.get());
		return n;
	}

	public void setEntity(Nota n) {
		ra.set(n.getRa());
		n1.set(n.getN1());
		n2.set(n.getN2());
	}
	
	public void adicionarNotas() {
		Nota n = getEntity();
		if (this.editando) {
			notaDao.atualizar(raAntigo, n);
		} else {
			notaDao.create(n);
		}
	}
	
	public void pesquisar() {
		List<Nota> tempLista = notaDao.pesquisarPorRA(ra.get());
		listaNota.clear();
		listaNota.addAll(tempLista);
	}
	
	public void apagar(Nota n) {
		notaDao.apagar(n);
	}
	
	public void editar() {
		this.editando = true;
		this.raAntigo = ra.get();
	}
	
	public void limpar() { 
		ra.set("");
		n1.set(0);
		n2.set(0);
		this.editando = false;
		this.raAntigo = null;
	}
	
	public StringProperty raProperty() {
		return ra;
	}
	
	public DoubleProperty n1Property() {
		return n1;
	}
	
	public DoubleProperty n2Property() {
		return n2;
	}
	
	public ObservableList<Nota> getListaNotas() {
		return listaNota;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}
	
	public String getNomeAntigo() {
		return raAntigo;
	}

	public void setNomeAntigo(String nomeAntigo) {
		this.raAntigo = nomeAntigo;
	}
}
