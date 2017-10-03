(function (loader) {
    'use strict';

    window.addEventListener("load", showAdvancedCameraIfMediaDeviceAvailable);

    function showAdvancedCameraIfMediaDeviceAvailable() {
        // https://developer.mozilla.org/en-US/docs/Web/API/WebRTC_API/Taking_still_photos
        var cameraConstraints = buildCameraConstraints();

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
            loader.show();
            disableCameraButtons();
            showAdvancedCamera();
            renderMediaStreamInCamera(mediaStream);
            enableButtonsWhenCameraIsReady();
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
        showAdvancedCameraSection('advancedCameraPreview');
    }

    function renderMediaStreamInCamera(mediaStream) {
        var videoElement = document.querySelector('video');
        videoElement.srcObject = mediaStream;

        videoElement.addEventListener('loadedmetadata', function () {
            videoElement.play();
        });
    }

    function enableButtonsWhenCameraIsReady() {
        var videoElement = document.querySelector('video');
        videoElement.addEventListener('canplay', function() {
            loader.hide();
            enableCameraButtons();
            takePictureWhenCameraButtonClicked();
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

    function takePictureWhenCameraButtonClicked() {
        var button = document.getElementById('cameraPreview');
        button.addEventListener('click', takePicture);
    }

    function takePicture(event) {
        event.preventDefault();

        var videoElement = document.querySelector('video');
        var width = videoElement.videoWidth;
        var height = videoElement.videoHeight;

        var canvasElement = document.querySelector('canvas');

        var context = canvasElement.getContext('2d');
        canvasElement.width = width;
        canvasElement.height = height;
        context.drawImage(videoElement, 0, 0, width, height);

        var data = canvasElement.toDataURL('image/jpg');
        displayCameraPreviewImage(data, width, height);
        updateCameraImageData(data);
        showAdvancedCameraSection('advancedCameraRender');
        resizePreviewImage(width, height);
    }

    function displayCameraPreviewImage(data, width, height) {
        var image = document.querySelector('img');
        image.setAttribute('src', data);
    }

    function resizePreviewImage(width, height) {
        var section = document.getElementById('advancedCameraRender');

        var widthRatio = width / section.clientWidth;
        var heightRatio = height / section.clientHeight;

        var maxRatio = Math.max(widthRatio, heightRatio);

        var image = document.querySelector('img');
        image.style.width = (width / maxRatio) + 'px';
        image.style.height = (height / maxRatio) + 'px';
    }

    function updateCameraImageData(data) {
        var input = document.getElementById('advancedCameraImage');
        input.value = data;
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