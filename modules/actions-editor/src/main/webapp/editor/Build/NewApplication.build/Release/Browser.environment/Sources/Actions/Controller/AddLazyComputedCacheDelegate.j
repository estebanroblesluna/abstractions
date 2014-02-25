@STATIC;1.0;I;21;Foundation/CPObject.jt;1067;objj_executeFile("Foundation/CPObject.j", NO);{var the_class = objj_allocateClassPair(CPObject, "AddLazyComputedCacheDelegate"),
meta_class = the_class.isa;class_addIvars(the_class, [new objj_ivar("_window"), new objj_ivar("_memcachedURLTF"), new objj_ivar("_ttlTF"), new objj_ivar("_keyExpressionTF"), new objj_ivar("_cacheExpressionsTF")]);objj_registerClassPair(the_class);
class_addMethods(the_class, [new objj_method(sel_getUid("add:"), function $AddLazyComputedCacheDelegate__add_(self, _cmd, aSender)
{
    var memcachedURL = objj_msgSend(self._memcachedURLTF, "stringValue");
    var ttl = objj_msgSend(self._ttlTF, "stringValue");
    var keyExpression = objj_msgSend(self._keyExpressionTF, "stringValue");
    var cacheExpressions = objj_msgSend(self._cacheExpressionsTF, "stringValue");
    objj_msgSend(objj_msgSend(objj_msgSend(self._window, "windowController"), "elementAPI"), "addLazyComputedCache:ttl:keyExpression:cacheExpressions:", memcachedURL, ttl, keyExpression, cacheExpressions);
    objj_msgSend(self._window, "close");
}
,["void","id"])]);
}