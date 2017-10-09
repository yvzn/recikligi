(function (loader) {
    'use strict';

    window.addEventListener("load", showAdvancedCameraIfMediaDeviceAvailable);

    var mediaSourceIds = [];

    function showAdvancedCameraIfMediaDeviceAvailable() {
        var cameraConstraints = buildCameraConstraints();

        loader.show();
        if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
            if (navigator.mediaDevices.enumerateDevices)
            {
                enumerateDevicesAndStartMediaStream();
            }
            else
            {
                startMediaStream(cameraConstraints);
            }
        }
    }

    function buildCameraConstraints(sourceId) {
        var constraints = {
            audio: false,
            video: {
                width: { ideal: 720 },
                height: { ideal: 1280 }
            }
        }
        if (sourceId) {
            constraints.video.deviceId = sourceId;
        }
        return constraints;
    }

    function enumerateDevicesAndStartMediaStream() {
        navigator.mediaDevices
            .enumerateDevices()
            .then(selectFirstSourceAndStartMediaStream)
            .catch(handleError);
    }

    function selectFirstSourceAndStartMediaStream(devices) {
        for (var i = 0; i != devices.length; ++i) {
            if (devices[i].kind == 'videoinput') {
                mediaSourceIds.push(devices[i].deviceId);
            }
        }
        // start with the first one by default
        mediaSourceIds.selectedIndex = 0;
        startMediaStreamFromSelectedSource();

        showSwitchDeviceButtonIfMoreThanOneSource();
    }

    function startMediaStreamFromSelectedSource() {
        var cameraConstraints = buildCameraConstraints(mediaSourceIds[mediaSourceIds.selectedIndex]);
        startMediaStream(cameraConstraints);
    }

    function startMediaStream(cameraConstraints) {
        navigator.mediaDevices
            .getUserMedia(cameraConstraints)
            .then(displayMediaStreamInAdvancedCamera)
            .catch(handleError);
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

    function showSwitchDeviceButtonIfMoreThanOneSource() {
        var button = document.getElementById('switchSource');
        if (mediaSourceIds && mediaSourceIds.length > 1) {
            button.addEventListener('click', switchMediaSource);
            button.style.display = 'flex';
        }
    }

    function switchMediaSource() {
        stopMediaStream();
        loader.show();
        selectNextMediaSource();
    }

    function stopMediaStream() {
        var videoElement = document.querySelector('video');

        var stream = videoElement.srcObject;
        var tracks = stream.getTracks();

        tracks.forEach(function(track) {
            track.stop();
        });
        videoElement.srcObject = null;
    }

    function selectNextMediaSource() {
        mediaSourceIds.selectedIndex = (mediaSourceIds.selectedIndex + 1) % mediaSourceIds.length;
        startMediaStreamFromSelectedSource();
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