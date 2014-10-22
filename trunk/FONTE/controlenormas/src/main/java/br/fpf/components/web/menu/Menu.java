package br.fpf.components.web.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String descricao;

    private static int count;

    private List<ItemMenu> itemMenus;

    public Menu( String descricao, String id ) {
    	count++;
        this.id = id != null ? id : count + "";
        this.descricao = descricao;
        this.itemMenus = new ArrayList<ItemMenu>();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + ( ( this.id == null ) ? 0 : this.id.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( this.getClass() != obj.getClass() ) {
            return false;
        }
        Menu other = (Menu) obj;
        if ( this.id == null ) {
            if ( other.id != null ) {
                return false;
            }
        } else if ( !this.id.equals( other.id ) ) {
            return false;
        }
        return true;
    }

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<ItemMenu> getItemMenus() {
		return itemMenus;
	}
	public void setItemMenus(List<ItemMenu> itemMenus) {
		this.itemMenus = itemMenus;
	}
}
