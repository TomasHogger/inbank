import axios from "axios";
import type {LoadRequest, LoadResponse} from "@/dto";

export async function checkLoanAvailability(loanRequest: LoadRequest): Promise<LoadResponse> {
    return (await axios.post("/api/check_loan_availability", loanRequest)).data
}