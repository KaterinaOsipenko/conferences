function copyPast(el) {
    var text = document.getElementById(el).value;

    navigator.clipboard.writeText(text)
        .then(() => {
            console.log('Text copied to clipboard');
        })
        .catch(err => {
            console.error('Error in copying text: ', err);
        });
}