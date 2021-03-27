function showModalError(msg) {
    $(".js-modal-msg").text(msg);
    let instance = M.Modal.getInstance($(".js-modal"));
    instance.open();
}

function drawAnnouncements(announcements) {
    let rsl = '<div class="row">';
    announcements.forEach(function(item, i, arr) {
        if (i !== 0 && i % 2 === 0) {
            rsl += '</div>';
            rsl += '<div class="row">';
        }
        rsl += '<div class="col s6">' +
                    '<div class="card auto-card">' +
                        '<p class="image-container">';
        if (item.car.carPhotos.length === 0) {
            rsl +=          '<img src="img/no-image.jpeg" width="212">';
        } else {
            rsl +=          '<img src="announcement/photo?name=' + item.car.carPhotos[0].id + '" width="212">';
        }
        rsl +=          '</p>' +
                        '<div class="auto-description">' +
                            '<p class="auto-link"><a href="announcement?page=view&id=' + item.id + '">' + item.car.carModel.name + '</a></p>' +
                            '<p class="auto-price">' + item.price + ' &#x20bd;</p>' +
                            '<p class="auto-properties">' +
                                item.car.mileage + ' км, ' + item.car.carBodyType.name + ', ' + item.car.carEngineType.name +
                            '</p>' +
                            '<p class="auto-city">' +
                                item.city.name +
                            '</p>' +
                        '</div>' +
                    '</div>' +
                '</div>';
    });
    rsl += '</div>';
    return rsl;
}

function getUrlParam(name){
    if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
        return decodeURIComponent(name[1]);
}

function showSelect(options, selectClass) {
    options.forEach(function (option) {
        let str = '<option value="' + option.id + '">' + option.name + '</option>';
        $(selectClass).append(str);
    });
}