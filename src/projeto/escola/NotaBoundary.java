package projeto.escola;

import java.sql.Time;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

public class NotaBoundary extends Application {
	TextField txtRA = new TextField("");
	TextField txtN1 = new TextField("");
	TextField txtN2 = new TextField("");
	private Button btnAddNotas = new Button("Adicionar Notas");
	private Button btnPesquisar = new Button("Pesquisar");
	private NotaControl control = new NotaControl();
	private TableView<Nota> table = new TableView<>();

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();
		GridPane gp = new GridPane();

		bp.setPadding(new Insets(6));

		Scene scn = new Scene(bp, 400, 300);
		bp.setTop(gp);
		bp.setCenter(table);

		prepararTable();

		gp.add(new Label("RA"), 0, 0);
		gp.add(txtRA, 1, 0);
		gp.add(new Label("N1"), 0, 1);
		gp.add(txtN1, 1, 1);
		gp.add(new Label("N2"), 0, 2);
		gp.add(txtN2, 1, 2);
		gp.add(btnAddNotas, 3, 0);
		gp.add(btnPesquisar, 3, 1);

		btnAddNotas.setOnAction(e -> {
			control.adicionarNotas();
			control.limpar();
			control.pesquisar();
		});

		btnPesquisar.setOnAction(e -> control.pesquisar());

		vincular();

		gp.setHgap(5.0);
		gp.setVgap(5.0);
		stage.setScene(scn);
		stage.setTitle("Gestão de Notas");
		stage.show();
	}

	private void prepararTable() {
		TableColumn<Nota, String> col1 = new TableColumn<>("RA");
		col1.setCellValueFactory(new PropertyValueFactory<Nota, String>("ra"));

		TableColumn<Nota, Double> col2 = new TableColumn<>("N1");
		col2.setCellValueFactory(new PropertyValueFactory<Nota, Double>("N1"));

		TableColumn<Nota, Double> col3 = new TableColumn<>("N2");
		col3.setCellValueFactory(new PropertyValueFactory<Nota, Double>("N2"));

		TableColumn<Nota, String> col4 = new TableColumn<>("ações");
		col4.setMinWidth(110);

		Callback<TableColumn<Nota, String>, TableCell<Nota, String>> cellFactory 
		= //
		new Callback<TableColumn<Nota, String>, TableCell<Nota, String>>() {
					@Override
					public TableCell call(final TableColumn<Nota, String> param) {
						final TableCell<Nota, String> cell = new TableCell<Nota, String>() {
							final Button btnApagar = new Button("Apagar");
							final Button btnEditar = new Button("Editar");

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btnApagar.setOnAction(event -> {
										Nota n = getTableView().getItems().get(getIndex());
										control.apagar(n);
										control.limpar();
										control.pesquisar();
									});
									btnEditar.setOnAction(event -> {
										Nota n = getTableView().getItems().get(getIndex()+1);
										control.setEntity(n);
										control.editar();
									});
									FlowPane fpanel = new FlowPane();
									fpanel.getChildren().addAll(btnEditar, btnApagar);
									setGraphic(fpanel);
									setText(null);
								}
							}
						};
						return cell;
					}
				};
		col4.setCellFactory(cellFactory);

		table.getColumns().clear();
		table.getColumns().addAll(col1, col2, col3, col4);
		table.setItems(control.getListaNotas());

		table.getSelectionModel().selectedItemProperty().addListener((propriedade, antiga, nova) -> {
			control.setEntity(nova);
		});
	}

	public void vincular() {
		Bindings.bindBidirectional(txtRA.textProperty(), control.raProperty());
		StringConverter<? extends Number> converterNumber = new DoubleStringConverter();
		Bindings.bindBidirectional(txtN1.textProperty(), control.n1Property(), (StringConverter) converterNumber);
		Bindings.bindBidirectional(txtN2.textProperty(), control.n2Property(), (StringConverter) converterNumber);
	}

	public static void main(String[] args) {
		Application.launch(NotaBoundary.class, args);
	}
}
