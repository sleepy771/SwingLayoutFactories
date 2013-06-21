package my.swing.utilities;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

//TODO make Class that there can be freely added components
/**
 * Class which handle GridBagConstaints object, in order to simplifying and shortening code for creating and setting panel layout.
 * Simple showcase:
 * <code>
 * JTextArea bigTopTextArea = ...
 * JPanel smallerPanelWithToogleButtons = ...
 * JPanel evenSmallerBottomLeftPanelWithOtherStaff = ...
 * 
 * SwingGBFactory factory = new SwingGBFactory<JPanel>(new JPanel()).setColumnWeights(0.4, 0.6).setRowWeights(0.7,0.3).fill();
 * factory.setSpread(2,1).add(bigTopTextArea).setSpread(1,1).addAll(smallerPanelWithToogleButtons, evenSmallerBottomLeftPanelWithOtherStaff);
 * JPanel panel = factory.build();
 * </code>
 * @author Filip Hornak [com(and_a_big_dot)gmail(Big_At)sleepy771]
 * @param <T>
 */
public class SwingGBFactory<T extends JComponent> {
	
	private T parent;
	private HashMap<JComponent, GridBagConstraints> hisKids = new HashMap<JComponent, GridBagConstraints>();
	private double[] columnWeights = null;
	private double[] rowWeights = null;
	
	private GridBagConstraints gbc;
	
	private int rows, columns;
	
	/**
	 * Constructor creates factory object, that should simplify work with GridBagLayout and inserting components to parent.
	 * 
	 * @param parent component that extends JComponent
	 */
	public SwingGBFactory(T parent, int rows, int columns){
		parent.setLayout(new GridBagLayout());
		this.parent = parent;
		this.rows = rows;
		this.columns = columns;
		this.columnWeights = new double[columns];
		this.rowWeights = new double[rows];
		gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
	}
	
	public SwingGBFactory<T> addComponent(JComponent component){
		setWeight();
		hisKids.put(component, getGridBagConstraints());
		next();
		return this;
	}
	
	public SwingGBFactory<T> addComponents(JComponent... components){
		for(int i=0, n = Math.min(rows*columns, components.length); i<n; i++){
			setWeight();
			hisKids.put(components[i], getGridBagConstraints());
			next();
		}
		return this;
	}
	
	public SwingGBFactory<T> addComponents(Collection<JComponent> components){
		return addComponents((JComponent[]) components.toArray());
	}
	
	public SwingGBFactory<T> setInsets(Insets insets){
		gbc.insets = insets;
		return this;
	}
	
	public SwingGBFactory<T> setInsets(int top, int left, int bottom, int right){
		return setInsets(new Insets(top, left, bottom, right));
	}
	
	public SwingGBFactory<T> setIpads(int ipadx, int ipady){
		gbc.ipadx = ipadx;
		gbc.ipady = ipady;
		return this;
	}
	
	public SwingGBFactory<T> fillBoth(){
		gbc.fill = GridBagConstraints.BOTH;
		return this;
	}
	
	public SwingGBFactory<T> fillHorizontal(){
		gbc.fill = GridBagConstraints.HORIZONTAL;
		return this;
	}
	
	public SwingGBFactory<T> fillVertical(){
		gbc.fill = GridBagConstraints.VERTICAL;
		return this;
	}
	
	public SwingGBFactory<T> doNotFill(){
		gbc.fill = GridBagConstraints.NONE;
		return this;
	}
	
	public SwingGBFactory<T> setAnchor(int anchor){
		gbc.anchor = anchor;
		return this;
	}
	
	public SwingGBFactory<T> centerAnchor(){
		return setAnchor(GridBagConstraints.CENTER);
	}
	
	public SwingGBFactory<T> northAnchor(){
		return setAnchor(GridBagConstraints.NORTH);
	}
	
	public SwingGBFactory<T> southAnchor(){
		return setAnchor(GridBagConstraints.SOUTH);
	}
	
	public SwingGBFactory<T> westAnchor(){
		return setAnchor(GridBagConstraints.WEST);
	}
	
	public SwingGBFactory<T> eastAnchor(){
		return setAnchor(GridBagConstraints.EAST);
	}
	
	public SwingGBFactory<T> setRowWeight(int index, double weight){
		this.rowWeights[index] = weight;
		return this;
	}
	
	public SwingGBFactory<T> setRowWeights(double... rowWeights){
		System.arraycopy(rowWeights, 0, this.rowWeights, 0, rows);
		return this;
	}
	
	public SwingGBFactory<T> setColumnWeight(int index, double weight){
		this.columnWeights[index] = weight;
		return this;
	}
	
	public SwingGBFactory<T> setColumnWeights(double... columnWeights){
		System.arraycopy(columnWeights, 0, this.columnWeights, 0, columns);
		return this;
	}
	
	private void setWeight(){
		this.gbc.weightx = columnWeights[gbc.gridx];
		this.gbc.weighty = rowWeights[gbc.gridy];
	}
	
	public GridBagConstraints getGridBagConstraints(){
		return (GridBagConstraints) this.gbc.clone();
	}
	
	private void next(){
		if(gbc.gridx < columns - 1){
			gbc.gridx++;
		}
		else{
			newRow();
		}
	}
	
	private void skip(int skip){
		if(gbc.gridx < columns - skip){
			gbc.gridx+=skip;
		}
		else{
			gbc.gridy = gbc.gridy + (gbc.gridx + skip)/columns;
			gbc.gridx = (gbc.gridx + skip)%columns;
			if(gbc.gridy >= rows){
				throw new IndexOutOfBoundsException();
			}
		}
	}
	
	private void newRow(){
		newRow(0);
	}
	
	private void newRow(int offset){
		skipRows(1, offset);
	}
	
	private void skipRows(int skip, int offset){
		if(offset >= 0 && offset < columns){
			if(gbc.gridy  < rows - skip){
				gbc.gridy+=skip;
				gbc.gridx = offset;
			}
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public Point getPosition(){
		return new Point(gbc.gridx, gbc.gridy);
	}
	
	public T build(){
		for(Map.Entry<JComponent, GridBagConstraints> entry : hisKids.entrySet()){
			parent.add(entry.getKey(), entry.getValue());
		}
		return parent;
	}
}
