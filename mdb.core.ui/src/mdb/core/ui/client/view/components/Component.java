package mdb.core.ui.client.view.components;


public class Component implements ISimpleVisualComponent {

	private String _name;
	private String _caption;	
	private String _hint;
	private String _img;
	
	public Component (String caption, String img) {
		setCaption(caption);
		setImg(img);		
	}
	
	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name=name;
	}

	@Override
	public String getCaption() {
		return _caption;
	}

	@Override
	public void setCaption(String value) {
		_caption = value;
	}

	@Override
	public String getImg() {
		return _img;
	}

	@Override
	public void setImg(String value) {
		_img = value;
	}

	@Override
	public String getHint() {
		return _hint;
	}

	@Override
	public void setHint(String value) {
		_hint = value;
	}

}
