package my.swing.utilities;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JComponent;

//TODO make Class that there can be freeli added components
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
	private TreeMap<JComponent, GridBagConstraints> hisKids = new TreeMap<JComponent, GridBagConstraints>();
	private ArrayList<Double> columnWeights = new ArrayList<Double>();
	private ArrayList<Double> rowWeights = new ArrayList<Double>();
	
	private GridBagConstraints gbc;
	
	/**
	 * Constructor creates factory object, that should simplify work with GridBagLayout and inserting components to parent.
	 * 
	 * @param parent component that extends JComponent
	 */
	public SwingGBFactory(T parent){
		this.parent = parent;
		gbc = new GridBagConstraints();
	}
	//TODO make method for switching between predefined Insets types
	/**
	 *  Method for creating insets for current usage of GridBagConstraints.
	 * @param top
	 * @param left
	 * @param bottom
	 * @param right
	 * @return SwingGBFactory object for some additional setups
	 */
	public SwingGBFactory<T> createtInsets(int top, int left, int bottom, int right){
		return setInsets(new Insets(top, left, bottom, right));
	}
	
	/**
	 * Method that sets default insets to GridBagConstarints.
	 * @param insets
	 * @return SwingGBFactory object for some additional setups
	 */
	public SwingGBFactory<T> setInsets(Insets insets){
		gbc.insets = insets;
		return this;
	}
	
	/**
	 * Sets position of component in grid, that would be added.
	 * @param position of row and column written as Point
	 * @return SwingGBFactory object for some additional setups
	 */
	public SwingGBFactory<T> setPosition(Point p){
		gbc.gridx = p.x;
		gbc.gridy = p.y;
		return this;
	}
	
	/**
	 * Sets position of component in grid, that would be added.
	 * @param gridx position of column
	 * @param gridy position of row
	 * @return SwingGBFactory object for some additional setups
	 */
	public SwingGBFactory<T> setPosition(int gridx, int gridy){
		this.gbc.gridx = gridx;
		this.gbc.gridy = gridy;
		return this;
	}
	
	/**
	 * Sets across how many cells would next added component spreads.
	 * @param columns
	 * @param rows
	 * @return
	 */
	public SwingGBFactory<T> setSpread(int columns, int rows){
		gbc.gridwidth = columns;
		gbc.gridheight = rows;
		return this;
	}
	
	//TODO make default weight to which it would be set when no match would be found.
	/**
	 * Sets weights to internal GridBagConstraints object. If requested row or column is not listed, it will sets to weight of last column or row.
	 * @param row
	 * @param column
	 */
	private void setWeights(int row, int column){
		if(column >= 0 && column < columnWeights.size()){
			gbc.weightx = columnWeights.get(column);
			
		}
		else{
			gbc.weightx = columnWeights.get(columnWeights.size() - 1);
		}
		if(row >= 0 && row < rowWeights.size()){
			gbc.weighty = rowWeights.get(row);
		}
		else{
			gbc.weighty = rowWeights.get(rowWeights.size() - 1);
		}
		
	}
	
	/*private void setWeights(Point p){
		setWeights(p.y, p.x);
	}*/
	/**
	 * Sets weights of cell on current position
	 */
	private void setWeights(){
		setWeights(gbc.gridy, gbc.gridx);
	}
	
	/**
	 * Sets the anchor of component.
	 * <code>
	 * 	... .setAnchor(GridBagConstraints.WEST). ... 
	 * </code>
	 * @param anchor
	 * @return
	 */
	public SwingGBFactory<T> setAnchor(int anchor){
		gbc.anchor = anchor;
		return this;
	}
	
	/**
	 * Sets anchor to center.
	 * @return
	 */
	public SwingGBFactory<T> setAnchorCenter(){
		gbc.anchor = GridBagConstraints.CENTER;
		return this;
	}
	
	/**
	 * Sets anchor to east.
	 * @return
	 */
	public SwingGBFactory<T> setAnchorEast(){
		gbc.anchor = GridBagConstraints.EAST;
		return this;
	}
	
	/**
	 * Sets anchor to west.
	 * @return
	 */
	public SwingGBFactory<T> setAnchorWest(){
		gbc.anchor = GridBagConstraints.WEST;
		return this;
	}
	
	/**
	 * Sets anchor to north.
	 * @return
	 */
	public SwingGBFactory<T> setAnchorNorth(){
		gbc.anchor = GridBagConstraints.NORTH;
		return this;
	}
	
	/**
	 * Sets anchor to south.
	 * @return
	 */
	public SwingGBFactory<T> setAnchorSouth(){
		gbc.anchor = GridBagConstraints.SOUTH;
		return this;
	}
	
	/**
	 * Next added components would fill cell horizontally.
	 * @return
	 */
	public SwingGBFactory<T> fillHorizontaly(){
		gbc.fill = GridBagConstraints.HORIZONTAL;
		return this;
	}
	
	/**
	 * Next added components would fill cell vertically.
	 * @return
	 */
	public SwingGBFactory<T> fillVertically(){
		gbc.fill = GridBagConstraints.VERTICAL;
		return this;
	}
	
	/**
	 * Next added components would fill whole space of cell.
	 * @return
	 */
	public SwingGBFactory<T> fill(){
		gbc.fill = GridBagConstraints.BOTH;
		return this;
	}
	
	/**
	 * Next added components woldn't fill cell in any direction. They 
	 * @return
	 */
	public SwingGBFactory<T> doNotFill(){
		gbc.fill = GridBagConstraints.NONE;
		return this;
	}
	
	/**
	 * Sets the position on left cell.
	 * @return
	 */
	public SwingGBFactory<T> goLeft(){
		this.gbc.gridx--;
		return this;
	}
	
	/**
	 * Sets the position on right cell.
	 * @return
	 */
	public SwingGBFactory<T> goRight(){
		this.gbc.gridx++;
		return this;
	}
	
	/**
	 * Sets the position on the cell above.
	 * @return
	 */
	public SwingGBFactory<T> goTop(){
		this.gbc.gridy++;
		return this;
	}
	
	/**
	 * Sets the position on the cell below.
	 * @return
	 */
	public SwingGBFactory<T> goBottom(){
		this.gbc.gridy--;
		return this;
	}
	
	/**
	 * Sets weight of columns in table.
	 * @param columns
	 * @return
	 */
	public SwingGBFactory<T> setColumnWeights(double... columns){
		columnWeights.clear();
		for(int i=0; i<columns.length; i++){
			columnWeights.add(columns[i]);
		}
		return this;
	}
	
	public SwingGBFactory<T> setColumnWeights(Collection<Double> columns){
		columnWeights.clear();
		columnWeights.addAll(columns);
		return this;
	}
	
	public SwingGBFactory<T> addColumnWeight(double weight){
		columnWeights.add(weight);
		return this;
	}
	
	public SwingGBFactory<T> setRowWeights(double... columns){
		rowWeights.clear();
		for(int i=0; i<columns.length; i++){
			rowWeights.add(columns[i]);
		}
		return this;
	}
	
	public SwingGBFactory<T> setRowWeights(Collection<Double> columns){
		rowWeights.clear();
		rowWeights.addAll(columns);
		return this;
	}
	
	public SwingGBFactory<T> newRow(int offset){
		gbc.gridx = offset;
		goBottom();
		return this;
	}
	
	public SwingGBFactory<T> newRow(){
		return newRow(0);
	}
	
	public SwingGBFactory<T> addRowWeight(double weight){
		rowWeights.add(weight);
		return this;
	}
	
	public SwingGBFactory<T> add(JComponent component){
		setWeights();
		hisKids.put(component, getGridBagConstraints());
		goRight();
		return this;
	}
	
	public SwingGBFactory<T> addRow(JComponent... kids){
		for(int i = 0; i < kids.length; i++){
			setWeights();
			hisKids.put(kids[i], getGridBagConstraints());
			goRight();
		}
		newRow();
		return this;
	}
	
	public SwingGBFactory<T> addRow(Collection<? extends JComponent> kids){
		return addRow((JComponent[]) kids.toArray());
	}
	
	public SwingGBFactory<T> addColumn(JComponent... kids){
		for(int i = 0; i < kids.length; i++){
			setWeights();
			hisKids.put(kids[i], getGridBagConstraints());
			goBottom();
		}
		return this;
	}
	
	public SwingGBFactory<T> addColumn(Collection<? extends JComponent> kids){
		return addColumn((JComponent[])kids.toArray());
	}
	
	public SwingGBFactory<T> addGrid(JComponent[][] components){
		int offset = getPosX();
		for(int i=0; i<components.length; i++){
			// make method newRow taht would save offset and itterates ower rows
			for(int j=0; j<components[i].length; j++){
				setWeights();
				hisKids.put(components[i][j], getGridBagConstraints());
				goRight();
			}
			newRow(offset);
		}
		return this;
	}
	
	/**
	 * Returns copy of curently selected GridBagConstrints
	 * @return
	 */
	public GridBagConstraints getGridBagConstraints(){
		// vzdy iba klonuje
		return (GridBagConstraints) this.gbc.clone();
	}
	
	public Point getPosition(){
		return new Point(gbc.gridx, gbc.gridy);
	}
	
	public int getPosX(){
		return gbc.gridx;
	}
	
	public int getPosY(){
		return gbc.gridy;
	}
	
	public double getColumnWeight(int column){
		if(column >= 0 && column < columnWeights.size()){
			return columnWeights.get(column);
		}
		else{
			return -1.;
		}
	}
	
	public double getRowWeight(int row){
		if(row >= 0 && row < rowWeights.size()){
			return rowWeights.get(row);
		}
		else{
			return -1.;
		}
	}
	
	public int getFill(){
		return gbc.fill;
	}
	
	public int getAnchor(){
		return gbc.anchor;
	}
	
	public T build(){
		for(Map.Entry<JComponent, GridBagConstraints> entry : hisKids.entrySet()){
			parent.add(entry.getKey(), entry.getValue());
		}
		return parent;
	}
}
