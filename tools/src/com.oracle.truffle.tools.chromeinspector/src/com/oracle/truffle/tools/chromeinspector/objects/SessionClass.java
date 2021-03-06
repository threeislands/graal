/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.truffle.tools.chromeinspector.objects;

import java.util.function.Supplier;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.tools.chromeinspector.InspectorExecutionContext;

/**
 * This object can only be instantiated to Inspector.Session().
 */
class SessionClass extends AbstractInspectorObject {

    private static final TruffleObject KEYS = new Keys();

    private Supplier<InspectorExecutionContext> contextSupplier;

    SessionClass(Supplier<InspectorExecutionContext> contextSupplier) {
        this.contextSupplier = contextSupplier;
    }

    public static boolean isInstance(TruffleObject obj) {
        return obj instanceof SessionClass;
    }

    @Override
    protected boolean isInstantiable() {
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    protected Object createNew(Object[] arguments) {
        return new Session(contextSupplier);
    }

    @Override
    protected TruffleObject getKeys() {
        return KEYS;
    }

    @Override
    protected boolean isField(String name) {
        return false;
    }

    @Override
    protected boolean isMethod(String name) {
        return false;
    }

    @Override
    protected Object getFieldValueOrNull(String name) {
        return null;
    }

    @Override
    protected Object invokeMethod(String name, Object[] arguments) {
        CompilerDirectives.transferToInterpreter();
        throw UnknownIdentifierException.raise(name);
    }

    static final class Keys extends AbstractInspectorArray {

        @Override
        int getLength() {
            return 0;
        }

        @Override
        Object getElementAt(int index) {
            CompilerDirectives.transferToInterpreter();
            throw UnknownIdentifierException.raise(Integer.toString(index));
        }
    }

}
