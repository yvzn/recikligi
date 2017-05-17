(function () {
    'use strict';

    window.onload = afterDocumentLoaded;

    function afterDocumentLoaded() {
        hideSubmitButtonIfJavascriptAvailable();
        autoSubmitFormIfImageSelected();
    }

    function hideSubmitButtonIfJavascriptAvailable() {
        document.styleSheets[0].insertRule('form input[type="submit"] { left: -1000px;}', 0);
    }

    function autoSubmitFormIfImageSelected() {
        var inputs = document.getElementsByTagName('input');
        for (var i = 0; i < inputs.length; ++i) {
            if (inputs[i].getAttribute('type') === 'file') {
                inputs[i].onchange = submitForm;
            }
        }
    }

    function submitForm() {
        var forms = document.getElementsByTagName('form');
        if (forms.length) {
            forms[0].submit();
        }
    }
})();