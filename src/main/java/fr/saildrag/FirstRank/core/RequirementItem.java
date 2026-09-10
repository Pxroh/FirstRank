package fr.saildrag.FirstRank.core;

import fr.maxlego08.menu.api.MenuItemStack;

public class RequirementItem {
    private MenuItemStack itemTrue;
    private MenuItemStack itemFalse;
    private String tinyLoreTrue;
    private String tinyLoreFalse;

    public void setItemTrue(MenuItemStack item){
        this.itemTrue = item;
    }

    public void setItemFalse(MenuItemStack item){
        this.itemFalse = item;
    }

    public MenuItemStack getItemTrue(){
        return this.itemTrue;
    }

    public MenuItemStack getItemFalse(){
        return this.itemFalse;
    }

    public void setTinyLoreTrue(String value){
        this.tinyLoreTrue = value;
    }

    public void setTinyLoreFalse(String value){
        this.tinyLoreFalse = value;
    }

    public String getTinyLoreTrue(){
        return this.tinyLoreTrue;
    }

    public String getTinyLoreFalse(){
        return this.tinyLoreFalse;
    }
}
