$(document).ready(function() {
    $('.js-modal').modal();
    loadFieldsData();
});

function loadFieldsData() {
    $.ajax({
        type: "POST",
        url: "announcement?action=get-form-fields"
    }).done(function(data) {
        showSelect(data.fields.cities, ".js-city");
        showSelect(data.fields.carModels, ".js-model");
        showSelect(data.fields.carBodyTypes, ".js-body_type");
        showSelect(data.fields.carEngineTypes, ".js-engine");
        showSelect(data.fields.carTransmissionBoxTypes, ".js-transmission_box");
        $('.js-announcement-type').val(data.fields.announcementType.id);
        $('.js-user').val(data.user.id);

        $('select').formSelect();
        $('.js-add-next').prop("disabled", false);
    }).fail(function(err) {
        let instance = M.Modal.getInstance($(".js-modal"));
        instance.open();
        showModalError("Ошибка на стороне сервера, перезагрузите страницу");
    });
}

$('.js-add-next').click(function () {
    let user = $(".js-user").val();
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
        "user": {"id": user},
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
    $.ajax({
        type: "POST",
        url: "announcement?action=save",
        contentType: "application/json",
        data: JSON.stringify(announcement),
    }).done(function(response) {
        $('.js-announcement-id').val(response.id);
        $(".js-auto-info-container").toggle();
        $(".js-photo-container").toggle();
    }).fail(function(err) {
        showModalError("Ошибка при сохранении объявления, перезагрузите страницу или повторите запрос позднее.");
    });
});