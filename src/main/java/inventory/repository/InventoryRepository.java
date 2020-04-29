package inventory.repository;


import inventory.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.StringTokenizer;

public class InventoryRepository {

	private String filename;
	private InventoryCRUD inventoryCRUD;

	public InventoryRepository(String filename) {
		this.inventoryCRUD =new InventoryCRUD();
		this.filename = filename;
		try {
			readEntities();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public InventoryRepository(String filename, InventoryCRUD inventoryCRUD){
		this.inventoryCRUD = inventoryCRUD;
		this.filename = filename;
		try {
			readEntities();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

    private Part getOutsourcePartFromString(String line){
        Part item=null;
        StringTokenizer st=new StringTokenizer(line, ",");
        st.nextToken();
        int id= Integer.parseInt(st.nextToken());
        inventoryCRUD.setAutoPartId(id);
        String name= st.nextToken();
        double price = Double.parseDouble(st.nextToken());
        int inStock = Integer.parseInt(st.nextToken());
        int minStock = Integer.parseInt(st.nextToken());
        int maxStock = Integer.parseInt(st.nextToken());
        String company=st.nextToken();
        item = new OutsourcedPart(id, name, price, inStock, minStock, maxStock, company);

        return item;
    }

	private Part getInhousePartFromString(String line){
		Part item=null;
		StringTokenizer st=new StringTokenizer(line, ",");
		st.nextToken();
        int id= Integer.parseInt(st.nextToken());
        inventoryCRUD.setAutoPartId(id);
        String name= st.nextToken();
        double price = Double.parseDouble(st.nextToken());
        int inStock = Integer.parseInt(st.nextToken());
        int minStock = Integer.parseInt(st.nextToken());
        int maxStock = Integer.parseInt(st.nextToken());
        int idMachine= Integer.parseInt(st.nextToken());
        item = new InhousePart(id, name, price, inStock, minStock, maxStock, idMachine);

		return item;
	}

	public void readEntities() throws IOException {
		ClassLoader classLoader = InventoryRepository.class.getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());

		ObservableList<Product> listProducts = FXCollections.observableArrayList();
		ObservableList<Part> listParts = FXCollections.observableArrayList();

		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String line = null;
			while ((line = br.readLine()) != null && !line.equals("")) {
				StringTokenizer st = new StringTokenizer(line, ",");
				String type = st.nextToken();
				switch (type) {
					case "I": {
						Part part = getInhousePartFromString(line);
							listParts.add(part);
						break;
					}
					case "O": {
						Part part = getOutsourcePartFromString(line);
							listParts.add(part);
						break;
					}
					case "P": {
						Product product = getProductFromString(line);
							listProducts.add(product);
						break;
					}
					default:
						break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		inventoryCRUD.setProducts(listProducts);
        inventoryCRUD.setAllParts(listParts);
	}

	private Product getProductFromString(String line){
		Product product=null;
		StringTokenizer st=new StringTokenizer(line, ",");
		st.nextToken();
		int id= Integer.parseInt(st.nextToken());
		inventoryCRUD.setAutoProductId(id);
        String name= st.nextToken();
        double price = Double.parseDouble(st.nextToken());
        int inStock = Integer.parseInt(st.nextToken());
        int minStock = Integer.parseInt(st.nextToken());
        int maxStock = Integer.parseInt(st.nextToken());
        String partIDs=st.nextToken();

        StringTokenizer ids= new StringTokenizer(partIDs,":");
        ObservableList<Part> list= FXCollections.observableArrayList();
        while (ids.hasMoreTokens()) {
            String idP = ids.nextToken();
            Part part = inventoryCRUD.lookupPart(idP);
            if (part != null)
                list.add(part);
        }
        product = new Product(id, name, price, inStock, minStock, maxStock, list);
        product.setAssociatedParts(list);
		return product;
	}

	public void writeAll() throws IOException{

		ClassLoader classLoader = InventoryRepository.class.getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());

		ObservableList<Part> parts= inventoryCRUD.getAllParts();
		ObservableList<Product> products= inventoryCRUD.getProducts();

		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			if (parts != null)
			for (Part p:parts) {
				System.out.println(p.toString());
				bw.write(p.toString());
				bw.newLine();
			}

			if (products != null)
			for (Product pr:products) {
				String line=pr.toString()+",";
				ObservableList<Part> list= pr.getAssociatedParts();
				int index=0;
				while(index<list.size()-1){
					line=line+list.get(index).getPartId()+":";
					index++;
				}
				if (index==list.size()-1)
					line=line+list.get(index).getPartId();
				bw.write(line);
				bw.newLine();
			}
			//bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addPart(Part part) throws RepositoryException {

		if(this.getAllParts().contains(part)){
			throw new RepositoryException("Duplicated part");
		}
		inventoryCRUD.addPart(part);
		try {
			writeAll();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public void addProduct(Product product){
		inventoryCRUD.addProduct(product);
		try {
			writeAll();
		}catch (IOException e){
			e.printStackTrace();
		}	}

	public int getAutoPartId(){
		return inventoryCRUD.getAutoPartId();
	}

	public int getAutoProductId(){
		return inventoryCRUD.getAutoProductId();
	}

	public ObservableList<Part> getAllParts(){
		return inventoryCRUD.getAllParts();
	}

	public ObservableList<Product> getAllProducts(){
		return inventoryCRUD.getProducts();
	}

	public Part lookupPart (String search){
		return inventoryCRUD.lookupPart(search);
	}

	public Product lookupProduct (String search){
		return inventoryCRUD.lookupProduct(search);
	}

	public void updatePart(int partIndex, Part part){
		inventoryCRUD.updatePart(partIndex, part);
		try {
			writeAll();
		}catch (IOException e){
			e.printStackTrace();
		}	}

	public void updateProduct(int productIndex, Product product){
		inventoryCRUD.updateProduct(productIndex, product);
		try {
			writeAll();
		}catch (IOException e){
			e.printStackTrace();
		}	}

	public void deletePart(Part part){
		inventoryCRUD.deletePart(part);
		try {
			writeAll();
		}catch (IOException e){
			e.printStackTrace();
		}	}

	public void deleteProduct(Product product){
		inventoryCRUD.removeProduct(product);
		try {
			writeAll();
		}catch (IOException e){
			e.printStackTrace();
		}	}

	public InventoryCRUD getInventoryCRUD(){
		return inventoryCRUD;
	}

	public void setInventoryCRUD(InventoryCRUD inventoryCRUD){
		this.inventoryCRUD = inventoryCRUD;
	}
}
