import { currentGET } from "../api";
export async function refresh() {
    await currentGET('url3');
}