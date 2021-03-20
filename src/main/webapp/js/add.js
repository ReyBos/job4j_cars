$(document).ready(function(){
    $('select').formSelect();
    $('.js-modal').modal();
});

$('.js-add-next').click(function () {
    let price = $(".js-price").val();
    let city = $(".js-city").val();
    let announcementType = $(".js-announcement-type").val();
    let isNew = $(".js-is_new").val();
    let mileage = $(".js-mileage").val();
    let isBroken = $(".js-is_broken").prop('checked');
    let description = $(".js-description").val();
    let model = $(".js-model").val();
    let bodyType = $(".js-body_type").val();
    let engine = $(".js-engine").val();
    let transmissionBoxType = $(".js-transmission_box").val();
    if (
        price === "" || city === "" || announcementType === "" || isNew === "" || mileage === ""
        || description === "" || model === "" || bodyType === "" || engine === ""
        || transmissionBoxType === ""
    ) {
        showModalError("Для продолжения необходимо заполнить все поля формы.");
        return false;
    }
    let announcement = {
        "price": price,
        "isSold": false,
        "city": {"id": city},
        "announcementType": {"id": announcementType},
        "car": {
            "isNew": JSON.parse(isNew),
            "mileage": mileage,
            "isBroken": isBroken,
            "description": description,
            "carModel" : {"id": model},
            "carBodyType": {"id": bodyType},
            "carEngineType": {"id": engine},
            "carTransmissionBoxType": {"id": transmissionBoxType}
        }
    };
    $(".js-auto-info-container").toggle();
    $(".js-photo-container").toggle();
    console.log(announcement);
});

function showModalError(msg) {
    $(".js-modal-msg").text(msg);
    let instance = M.Modal.getInstance($(".js-modal"));
    instance.open();
}