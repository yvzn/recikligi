(function (loader) {
    'use strict';

    window.addEventListener("load", showAdvancedCameraIfMediaDeviceAvailable);

    function showAdvancedCameraIfMediaDeviceAvailable() {
        // https://developer.mozilla.org/en-US/docs/Web/API/WebRTC_API/Taking_still_photos
        var cameraConstraints = buildCameraConstraints();

        loader.show();
        if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
            navigator.mediaDevices
                .getUserMedia(cameraConstraints)
                .then(displayMediaStreamInAdvancedCamera)
                .catch(handleError);
        }
    }

    function buildCameraConstraints() {
        return {
            audio: false,
            video: {
                width: { ideal: 720 },
                height: { ideal: 1280 }
            }
        }
    }

    function displayMediaStreamInAdvancedCamera(mediaStream) {
        try {
            disableCameraButtons();
            showAdvancedCamera();
            renderMediaStreamInCameraVideoPreview(mediaStream);
            enableButtonsWhenVideoPreviewIsReady();
        } catch (e) {
            handleError(e);
        }
    }

    function handleError(err) {
        console.error(err);
        showClassicCamera();
        loader.hide();
    }

    function showClassicCamera() {
        document.body.className = 'camera';
    }

    function showAdvancedCamera() {
        document.body.className = 'camera advanced';
        showAdvancedCameraSection('advancedCameraVideoPreview');
    }

    function renderMediaStreamInCameraVideoPreview(mediaStream) {
        var videoElement = document.querySelector('video');
        videoElement.srcObject = mediaStream;
        loader.hide();

        videoElement.addEventListener('loadedmetadata', function () {
            videoElement.play();
        });
    }

    function enableButtonsWhenVideoPreviewIsReady() {
        var videoElement = document.querySelector('video');
        videoElement.addEventListener('playing', function() {
            enableCameraButtons();
            takePictureWhenButtonClicked();
            retryWhenButtonClicked();
        })
    }

    function disableCameraButtons() {
        setCameraButtonsClassName('disabled');
    }

    function enableCameraButtons() {
        setCameraButtonsClassName('');
    }

    function setCameraButtonsClassName(className) {
        var buttons = document.querySelectorAll('section label[for]');
        for (var i = 0; i < buttons.length; ++i) {
            buttons[i].className = className;
        }
    }

    function takePictureWhenButtonClicked() {
        var button = document.getElementById('takeStillPicture');
        button.addEventListener('click', takePicture);
    }

    function takePicture(event) {
        event.preventDefault();

        var stillPicture = extractStillPictureDataFromVideo();

        displayCameraStillPicture(stillPicture.dataURL, stillPicture.width, stillPicture.height);
        updateAdvancedCameraImageFormField(stillPicture.dataURL);

        showAdvancedCameraSection('advancedCameraStillImage');
        resizeCameraStillPicture(stillPicture.width, stillPicture.height);
    }

    function extractStillPictureDataFromVideo() {
        var videoElement = document.querySelector('video');
        var width = videoElement.videoWidth;
        var height = videoElement.videoHeight;

        var canvasElement = document.querySelector('canvas');

        var context = canvasElement.getContext('2d');
        canvasElement.width = width;
        canvasElement.height = height;
        context.drawImage(videoElement, 0, 0, width, height);

        return {
            dataURL: canvasElement.toDataURL('image/jpg'),
            width: width,
            height: height
        }
    }

    function displayCameraStillPicture(dataURL, width, height) {
        var image = document.querySelector('img');
        image.setAttribute('src', dataURL);
    }

    function resizeCameraStillPicture(width, height) {
        var section = document.getElementById('advancedCameraStillImage');

        var widthRatio = width / section.clientWidth;
        var heightRatio = height / section.clientHeight;

        var maxRatio = Math.max(widthRatio, heightRatio);

        var image = document.querySelector('img');
        image.style.width = (width / maxRatio) + 'px';
        image.style.height = (height / maxRatio) + 'px';
    }

    function updateAdvancedCameraImageFormField(dataURL) {
        var input = document.getElementById('advancedCameraImage');
        //trim the data:....base64, header
        var pos = dataURL.indexOf("base64,");
        var data = pos > -1 ? dataURL.substring(pos + "base64,".length) : dataURL;
        input.value = data;
    }

    function retryWhenButtonClicked() {
        var button = document.getElementById('retry');
        button.addEventListener('click', retryTakingPicture);
    }

    function retryTakingPicture() {
        showAdvancedCamera();
        updateAdvancedCameraImageFormField('');
    }

    function showAdvancedCameraSection(id) {
        var sections = document.querySelectorAll('section');
        for (var i = 0; i < sections.length; ++i) {
            if (sections[i].id == id) {
                sections[i].className = 'active';
            } else {
                sections[i].className = '';
            }
        }
    }
})(loader);