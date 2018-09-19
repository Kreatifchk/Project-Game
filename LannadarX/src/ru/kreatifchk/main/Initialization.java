package ru.kreatifchk.main;

import javax.swing.UIManager;

import ru.kreatifchk.game.MonsterList;
import ru.kreatifchk.game.TilesList;

public class Initialization {
	
	public Initialization() {
		new TilesList();
		new Fonts();
		new ImageInit();
		
		new MonsterList();
		
		initOthers();
	}
	
	private void initOthers() {
		//Локализация JFileChooser
		UIManager.put("FileChooser.fileNameHeaderText", "Имя");
		UIManager.put("FileChooser.acceptAllFileFilterText", "Все файлы");
		UIManager.put("FileChooser.homeFolderToolTipText", "Домой");
		UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
		UIManager.put("FileChooser.fileTypeHeaderText", "Тип");
		UIManager.put("FileChooser.fileSizeHeaderText", "Размер");
		UIManager.put("FileChooser.upFolderToolTipText", "Вверх");
		UIManager.put("FileChooser.newFolderToolTipText", "Создать папку");
		UIManager.put("FileChooser.detailsViewButtonToolTipText", "Подробно");
		UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
		UIManager.put("FileChooser.viewMenuLabelText", "Настроить вид");
		UIManager.put("FileChooser.refreshActionLabelText", "Обновить");
		UIManager.put("FileChooser.newFolderActionLabelText", "Новая папка");
		UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файлов");
		UIManager.put("FileChooser.lookInLabelText", "Смотреть в");
		UIManager.put("FileChooser.fileNameHeaderText", "Имя");
		UIManager.put("FileChooser.openButtonText", "Открыть");
		UIManager.put("FileChooser.saveButtonText", "Сохранить");
		UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить");
		UIManager.put("FileChooser.openButtonToolTipText", "Открыть");
		UIManager.put("FileChooser.cancelButtonText", "Отмена");
		UIManager.put("FileChooser.cancelButtonText", "Отмена");
		UIManager.put("FileChooser.openDialogTitleText", "Открыть");
		UIManager.put("FileChooser.saveDialogTitleText", "Сохранить");
	}
	
}
