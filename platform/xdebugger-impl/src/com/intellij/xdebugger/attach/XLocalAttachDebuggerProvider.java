// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.xdebugger.attach;

import com.intellij.execution.process.ProcessInfo;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface XLocalAttachDebuggerProvider extends XAttachDebuggerProvider {
  ExtensionPointName<XAttachDebuggerProvider> EP = ExtensionPointName.create("com.intellij.xdebugger.localAttachDebuggerProvider");

  List<XLocalAttachDebugger> getAvailableDebuggers(@NotNull Project project,
                                                   @NotNull ProcessInfo process,
                                                   @NotNull UserDataHolder contextHolder);

  @NotNull
  @Override
  default XAttachPresentationGroup<ProcessInfo> getPresentationGroup() {
    return XDefaultLocalAttachGroup.INSTANCE;
  }

  @Override
  default boolean isAttachHostApplicable(@NotNull XAttachHost attachHost) {
    return attachHost instanceof LocalAttachHost;
  }

  @NotNull
  @Override
  default List<XAttachDebugger> getAvailableDebuggers(@NotNull Project project,
                                                      @NotNull XAttachHost hostInfo,
                                                      @NotNull ProcessInfo process,
                                                      @NotNull UserDataHolder contextHolder) {
    assert hostInfo instanceof LocalAttachHost;

    return new ArrayList<>(getAvailableDebuggers(project, process, contextHolder));
  }
}
