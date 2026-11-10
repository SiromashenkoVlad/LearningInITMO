async function getData() {
    const url = "fcgi-bin/server.jar";
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Response status: $
            {response.status}`);
        }
    const json = await response.json();
    console.log(json);
    } catch (error) {
        console.error(error.message);
    }
}

console.log(getData())

