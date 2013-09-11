CodeMirror.defineMode("dust", function() {
	return {
		startState : function() {
			return {
				inTag : false
			};
		},
		token : function(stream, state) {
			if (!state.inTag) {
				if (stream.match(/^\{[a-zA-Z]/)) {
					state.inTag = true;
				} else {
					stream.match(/^\{/);
				}
			}
			if (state.inTag) {
				if (stream.skipTo('}')) {
					stream.next();
					state.inTag = false;
				} else {
					stream.skipToEnd();
				}
				return "tag";
			} else {
				stream.skipTo('{') || stream.skipToEnd();
				return null;
			}
		}
	};
});