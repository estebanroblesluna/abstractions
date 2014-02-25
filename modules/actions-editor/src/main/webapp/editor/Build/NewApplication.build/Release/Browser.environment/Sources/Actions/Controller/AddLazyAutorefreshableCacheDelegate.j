@STATIC;1.0;I;21;Foundation/CPObject.jt;1173;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "AddLazyAutorefreshableCacheDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_memcachedURLTF"), new objj_ivar("_oldCacheEntryInMillsTF"), new objj_ivar("_keyExpressionTF"), new objj_ivar("_cacheExpressionsTF")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("add:"), function $AddLazyAutorefreshableCacheDelegate__add_(self, _cmd, aSender)
{
    var memcachedURL = objj_msgSend(self._memcachedURLTF, "stringValue");
    var oldCacheEntryInMills = objj_msgSend(self._oldCacheEntryInMillsTF, "stringValue");
    var keyExpression = objj_msgSend(self._keyExpressionTF, "stringValue");
    var cacheExpressions = objj_msgSend(self._cacheExpressionsTF, "stringValue");
    objj_msgSend(objj_msgSend(objj_msgSend(self._window, "windowController"), "elementAPI"), "addLazyAutorefreshableCache:oldCacheEntryInMills:keyExpression:cacheExpressions:", memcachedURL, oldCacheEntryInMills, keyExpression, cacheExpressions);
    objj_msgSend(self._window, "close");
}
,["void","id"])]);
}