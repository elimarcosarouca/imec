var version = navigator.appVersion;

/* Desabilita o refresh na pg. */
function disableRefresh(ev) {
	var keycode = (window.event) ? event.keyCode : ev.keyCode;

	if ((version.indexOf('MSIE') != -1)) {
		if (keycode == 116) {	// key F5
			ev.keyCode = 0;
			ev.returnValue = false;
			return false;
		}
		if (keycode == 82) {	// key R
			if (ev.ctrlKey) {
				ev.returnValue = false;
				ev.keyCode = 0;
				return false;
			}
		}
	} else {
		if (keycode == 116) {
			return false;
		}
		if (keycode == 82) {	// key R
			if (ev.ctrlKey) {
				ev.returnValue = false;
				ev.keyCode = 0;
				return false;
			}
		}
	}
}


