import {LoadRequest, LoadResponse} from "../src/dto";
import {AxiosError, AxiosResponse} from "axios";

export async function checkLoanAvailability(loanRequest: LoadRequest): Promise<LoadResponse> {
    switch (loanRequest.personalCode) {
        case "49002010965":
            return {
                approved: false,
                maxAmount: null
            }
        case "49002010976":
            return {
                approved: true,
                maxAmount: 5000
            }
        case "49002010987":
            return {
                approved: false,
                maxAmount: 2000
            }
        case "49002010998":
            return {
                approved: true,
                maxAmount: 10000
            }
        default:
            throw new AxiosError(
                "Unknown account",
                "400",
                null,
                null,
                {
                    data: "Unknown account"
                } as AxiosResponse
            )
    }
}