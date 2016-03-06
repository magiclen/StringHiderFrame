/*
 *
 * Copyright 2015-2016 magiclen.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.magiclen.stringhiderframe.pages;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.magiclen.magicstringhider.StringHider;

/**
 * 主頁面。
 *
 * @author Magic Len
 */
public class Main extends VBox {

    // -----類別常數-----
    private static final int GAP = 5;

    // -----類別變數-----
    // -----物件常數-----
    private final Insets insets;
    private final TextArea taSource, taDestination;
    private final TextField tfKey;
    private final Button bCopy;

    // -----建構子-----
    public Main() {
	insets = new Insets(GAP, GAP, GAP, GAP);

	// GUI元件
	taSource = new TextArea();

	taDestination = new TextArea();
	taDestination.setEditable(false);

	tfKey = new TextField();
	tfKey.setPromptText("Key");

	bCopy = new Button("Copy");
	bCopy.setMaxWidth(Integer.MAX_VALUE);
	bCopy.setOnAction((e) -> {
	    // 複製結果到剪貼簿
	    final Clipboard clipboard = Clipboard.getSystemClipboard();
	    final ClipboardContent content = new ClipboardContent();
	    content.putString(taDestination.getText());
	    clipboard.setContent(content);
	});

	VBox.setVgrow(taSource, Priority.ALWAYS);
	VBox.setVgrow(taDestination, Priority.ALWAYS);
	VBox.setVgrow(bCopy, Priority.SOMETIMES);
	VBox.setMargin(taSource, insets);
	VBox.setMargin(taDestination, insets);
	VBox.setMargin(tfKey, insets);
	VBox.setMargin(bCopy, insets);
	setAlignment(Pos.CENTER);
	final ObservableList<Node> children = getChildren();
	children.addAll(taSource, taDestination, tfKey, bCopy);

	addListeners();
    }

    private void addListeners() {
	taSource.textProperty().addListener((e) -> {
	    handle();
	});

	tfKey.textProperty().addListener((e) -> {
	    handle();
	});
    }

    private void handle() {
	final String text = taSource.getText().trim();
	final String key = tfKey.getText().trim();

	final String decompressedText = StringHider.decompression(text);
	final String recoveredText = StringHider.recoverString(decompressedText, key);
	if (recoveredText != null && recoveredText.length() > 0) {
	    taDestination.setText(recoveredText);
	} else {
	    taDestination.setText(StringHider.compression(StringHider.hideString(text, key)));
	}
    }
}
